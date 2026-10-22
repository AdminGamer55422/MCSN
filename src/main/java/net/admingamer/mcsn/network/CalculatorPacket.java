package net.admingamer.mcsn.network;

import net.admingamer.mcsn.client.CalculatorScreen;
import net.admingamer.mcsn.util.MCSNMath;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraftforge.network.NetworkEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Supplier;

public class CalculatorPacket {
    private final String op;
    private final String num1;
    private final String num2;
    private final String num3;

    public CalculatorPacket(String op, String num1, String num2, String num3) {
        this.op = op;
        this.num1 = num1;
        this.num2 = num2;
        this.num3 = num3;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(op);
        buf.writeUtf(num1);
        buf.writeUtf(num2);
        buf.writeUtf(num3);
    }

    public static CalculatorPacket fromBytes(FriendlyByteBuf buf) {
        return new CalculatorPacket(buf.readUtf(), buf.readUtf(), buf.readUtf(), buf.readUtf());
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;

            try {
                BigDecimal number1 = new BigDecimal(num1);
                BigDecimal number2 = num2.isEmpty() ? BigDecimal.ZERO : new BigDecimal(num2);
                BigDecimal number3 = num3.isEmpty() ? BigDecimal.ZERO : new BigDecimal(num3);
                double n1 = number1.doubleValue();
                double n2 = number2.doubleValue();
                double n3 = number3.doubleValue();
                float f1 = number1.floatValue();
                float f2 = number2.floatValue();
                float f3 = number3.floatValue();
                BigDecimal ans = BigDecimal.ZERO;

                switch (op) {
                    case "+" -> ans = number1.add(number2);
                    case "-" -> ans = number1.subtract(number2);
                    case "×" -> ans = number1.multiply(number2);
                    case "÷" -> ans = number1.divide(number2, 14, RoundingMode.HALF_UP);
                    case "%" -> ans = number1.multiply(number2.multiply(BigDecimal.valueOf(0.01)));

                    case "max" -> ans = number1.max(number2);
                    case "min" -> ans = number1.min(number2);
                    case "^" -> ans = BigDecimal.valueOf(Math.pow(n1, n2));
                    case "!" -> {
                        int target = (int) Math.floor(n1);
                        if (target < 0) throw new IllegalArgumentException("Cannot calculate factorial of a negative number!");

                        BigDecimal result = BigDecimal.ONE;
                        for (int i = 1; i <= target; i++) {
                            result = result.multiply(BigDecimal.valueOf(i));
                        }
                        ans = result;
                    }
                    case "|a|" -> ans = BigDecimal.valueOf(Math.abs(n1));
                    case "⌈a⌉" -> ans = BigDecimal.valueOf(Math.ceil(n1));
                    case "acos" -> ans = BigDecimal.valueOf(Math.acos(n1));
                    case "asin" -> ans = BigDecimal.valueOf(Math.asin(n1));
                    case "atan" -> ans = BigDecimal.valueOf(Math.atan(n1));
                    case "atan2" -> ans = BigDecimal.valueOf(Math.atan2(n1, n2));
                    case "³√" -> ans = BigDecimal.valueOf(Math.cbrt(n1));
                    case "sign" -> ans = BigDecimal.valueOf(Math.copySign(n1, n2));
                    case "exp" -> ans = BigDecimal.valueOf(Math.exp(n1));
                    case "expm1" -> ans = BigDecimal.valueOf(Math.expm1(n1));
                    case "⌊a⌋" -> ans = BigDecimal.valueOf(Math.floor(n1));
                    case "log" -> ans = BigDecimal.valueOf(Math.log(n1));
                    case "log_b" -> {
                        if (n1 <= 0 || n2 <= 0 || n2 == 1) {
                            throw new IllegalArgumentException("Invalid logarithms bases/values.");
                        }
                        double logResult = Math.log(n1) / Math.log(n2);
                        ans = BigDecimal.valueOf(logResult);
                    }
                    case "log10" -> ans = BigDecimal.valueOf(Math.log10(n1));
                    case "log1p" -> ans = BigDecimal.valueOf(Math.log1p(n1));
                    case "mod" -> {
                        BigDecimal[] remainderArray = number1.divideAndRemainder(number2);
                        ans = remainderArray[1];
                    }
                    case "random" -> ans = BigDecimal.valueOf(Math.random());
                    case "round" -> ans = BigDecimal.valueOf(Math.round(n1));
                    case "scalb" -> ans = BigDecimal.valueOf(Math.scalb(n1, (int)n2));
                    case "√" -> ans = BigDecimal.valueOf(Math.sqrt(n1));
                    case "a²" -> ans = BigDecimal.valueOf(Mth.square(n1));
                    case "deg" -> ans = BigDecimal.valueOf(Math.toDegrees(n1));
                    case "rad" -> ans = BigDecimal.valueOf(Math.toRadians(n1));
                    case "ulp" -> ans = BigDecimal.valueOf(Math.ulp(n1));

                    case "m_add" -> {
                        net.minecraft.nbt.CompoundTag persistentData = player.getPersistentData();
                        String currentMemStr = persistentData.getString("mcsn_calculator_memory");
                        BigDecimal currentMem = currentMemStr.isEmpty() ? BigDecimal.ZERO : new BigDecimal(currentMemStr);

                        BigDecimal valueToAdd = ans.equals(BigDecimal.ZERO) ? number1 : ans;
                        ans = currentMem.add(valueToAdd);

                        persistentData.putString("mcsn_calculator_memory", ans.toPlainString());
                        player.sendSystemMessage(Component.literal("§b// Memory M+: Added " + valueToAdd.stripTrailingZeros().toPlainString() + " (Total: " + ans.stripTrailingZeros().toPlainString() + ")"));
                        context.setPacketHandled(true);
                        return;
                    }
                    case "m_recall" -> {
                        net.minecraft.nbt.CompoundTag persistentData = player.getPersistentData();
                        String currentMemStr = persistentData.getString("mcsn_calculator_memory");
                        ans = currentMemStr.isEmpty() ? BigDecimal.ZERO : new BigDecimal(currentMemStr);

                        player.sendSystemMessage(Component.literal("§bMemory MR: " + ans.stripTrailingZeros().toPlainString()));
                        String display = "Memory = " + ans.stripTrailingZeros().toPlainString();
                        player.sendSystemMessage(Component.literal("§aResult: " + display));

                        final String finalValue = ans.stripTrailingZeros().toPlainString();
                        context.enqueueWork(() -> {
                            if (net.minecraft.client.Minecraft.getInstance().screen instanceof CalculatorScreen calcScreen) {
                                calcScreen.receiveMemoryValueFromServer(finalValue);
                            }
                        });

                        context.setPacketHandled(true);
                        return;
                    }
                    case "m_clear" -> {
                        player.getPersistentData().putString("mcsn_calculator_memory", "0");
                        player.sendSystemMessage(Component.literal("§eMemory MC: Cleared memory slot bank."));
                        context.setPacketHandled(true);
                        return;
                    }

                    case "mth_sin" -> ans = BigDecimal.valueOf(Math.sin(n1));
                    case "mth_sinh" -> ans = BigDecimal.valueOf(Math.sinh(n1));
                    case "mth_tan" -> ans = BigDecimal.valueOf(Math.tan(n1));
                    case "mth_tanh" -> ans = BigDecimal.valueOf(Math.tanh(n1));
                    case "mth_cos" -> ans = BigDecimal.valueOf(Math.cos(n1));
                    case "mth_cosh" -> ans = BigDecimal.valueOf(Math.cosh(n1));
                    case "mth_ceil" -> ans = BigDecimal.valueOf(Mth.ceil(n1));
                    case "mth_clamp" -> ans = BigDecimal.valueOf(Mth.clamp(n1, n2, n3));
                    case "mth_clampedLerp" -> ans = BigDecimal.valueOf(Mth.clampedLerp(n1, n2, n3));
                    case "mth_wrap" -> ans = BigDecimal.valueOf(Mth.wrapDegrees(n1));
                    case "mth_color" -> ans = BigDecimal.valueOf(Mth.color(f1, f2, f3));
                    case "mth_hsvToRgb" -> ans = BigDecimal.valueOf(Mth.hsvToRgb(f1, f2, f3));
                    case "mth_lerp" -> ans = BigDecimal.valueOf(Mth.lerp(n1, n2, n3));
                    case "mth_inverseLerp" -> ans = BigDecimal.valueOf(Mth.inverseLerp(f1, f2, f3));
                    case "mth_triangleWave" -> ans = BigDecimal.valueOf(Mth.triangleWave(f1, f2));
                    case "mth_wobble" -> ans = BigDecimal.valueOf(Mth.wobble(n1));

                    case "mth_acosh" -> ans = BigDecimal.valueOf(MCSNMath.acosh(n1));
                    case "mth_asinh" -> ans = BigDecimal.valueOf(MCSNMath.asinh(n1));
                    case "mth_atanh" -> ans = BigDecimal.valueOf(MCSNMath.atanh(n1));
                    case "mth_csc" -> ans = BigDecimal.valueOf(MCSNMath.csc(n1));
                    case "mth_csch" -> ans = BigDecimal.valueOf(MCSNMath.csch(n1));
                    case "mth_acsc" -> ans = BigDecimal.valueOf(MCSNMath.acsc(n1));
                    case "mth_acsch" -> ans = BigDecimal.valueOf(MCSNMath.acsch(n1));
                    case "mth_sec" -> ans = BigDecimal.valueOf(MCSNMath.sec(n1));
                    case "mth_sech" -> ans = BigDecimal.valueOf(MCSNMath.sech(n1));
                    case "mth_asec" -> ans = BigDecimal.valueOf(MCSNMath.asec(n1));
                    case "mth_asech" -> ans = BigDecimal.valueOf(MCSNMath.asech(n1));
                    case "mth_cot" -> ans = BigDecimal.valueOf(MCSNMath.cot(n1));
                    case "mth_coth" -> ans = BigDecimal.valueOf(MCSNMath.coth(n1));
                    case "mth_acot" -> ans = BigDecimal.valueOf(MCSNMath.acot(n1));
                    case "mth_acoth" -> ans = BigDecimal.valueOf(MCSNMath.acoth(n1));
                }

                boolean isBinaryOp = op.equals("+") || op.equals("-") || op.equals("×") ||
                        op.equals("÷") || op.equals("%") || op.equals("max") ||
                        op.equals("min") || op.equals("^") || op.equals("mth_clamp") ||
                        op.equals("log_b");

                String s1 = number1.toPlainString();
                String s2 = number2.toPlainString();
                String s3 = number3.toPlainString();
                String sAns = ans.stripTrailingZeros().toPlainString();

                String display = switch (op) {
                    case "|a|" -> "|" + s1 + "| = " + sAns;
                    case "⌈a⌉" -> "⌈" + s1 + "⌉ = " + sAns;
                    case "⌊a⌋" -> "⌊" + s1 + "⌋ = " + sAns;
                    case "√" -> "√(" + s1 + ") = " + sAns;
                    case "³√" -> "³√(" + s1 + ") = " + sAns;
                    case "a²" -> s1 + "² = " + sAns;
                    case "!" -> s1 + "! = " + sAns;
                    case "deg" -> s1 + "to degrees = " + sAns;
                    case "rad" -> s1 + "to radians = " + sAns;

                    case "log_b" -> "log_" + s2 + "(" + s1 + ") = " + sAns + Math.scalb(3, 3);
                    case "mth_clamp" -> "clamp(" + s1 + ", min=" + s2 + ", max=" + s3 + ") = " + sAns;
                    case "mth_clampedLerp" -> "clampedLerp(" + s1 + ", min=" + s2 + ", max=" + s3 + ") = " + sAns;
                    case "mth_wrap" -> "wrapDegrees(" + s1 + ") = " + sAns + "°";
                    case "mth_lerp" -> "lerp(" + s1 + ", min=" + s2 + ", max=" + s3 + ") = " + sAns;
                    case "mth_inverseLerp" -> "inverseLerp(" + s1 + ", min=" + s2 + ", max=" + s3 + ") = " + sAns;
                    case "mth_color" -> "color(r=" + s1 + ", b=" + s2 + ", g=" + s3 + ") = " + sAns;
                    case "mth_hsvToRgb" -> "HSV(h=" + s1 + ", s=" + s2 + ", v=" + s3 + ") to RGB = " + sAns;

                    case "×" -> s1 + " · " + s2 + " = " + sAns;
                    case "÷" -> s1 + "/" + s2 + " = " + sAns;
                    case "%" -> s1 + "% of " + s2 + " = " + sAns;
                    case "mod" -> "mod(" + s1 + ", " + s2 + ") = " + sAns;
                    case "scalb" -> s1 + " · 2^" + s2 + " = " + sAns;
                    case "mth_triangleWave" -> "triangleWave(" + s1 + ", " + s2 + ") = " + sAns;
                    default -> {
                        if (isBinaryOp) {
                            yield s1 + " " + op + " " + s2 + " = " + sAns;
                        } else {
                            String cleanOpName = op.startsWith("mth_") ? op.substring(4) : op;
                            yield cleanOpName + "(" + s1 + ") = " + sAns;
                        }
                    }
                };

                player.sendSystemMessage(Component.literal("§aResult: " + display));

            } catch (Exception e) {
                player.sendSystemMessage(Component.literal("§cError: " + e.getMessage()));
            }
        });
        context.setPacketHandled(true);
    }
}
