package net.admingamer.mcsn.mixin;

import net.minecraft.world.level.material.MapColor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class MapColorAccessor {

    public static MapColor createTrueRGBColor(int hexColor) {
        try {
            // Allocate an isolated instance directly in memory (Bypasses vanilla constructor limit arrays!)
            Field unsafeField = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            sun.misc.Unsafe unsafe = (sun.misc.Unsafe) unsafeField.get(null);
            MapColor instance = (MapColor) unsafe.allocateInstance(MapColor.class);

            // Explicitly force map index ID to 0 (Stone baseline) so the engine passes structural checks,
            // but change the underlying color parameters to hold your exact RGB hex choice!
            int intFieldIndex = 0;
            for (Field field : MapColor.class.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers()) && field.getType() == int.class) {
                    field.setAccessible(true);
                    if (intFieldIndex == 0) field.setInt(instance, 0);         // Safe ID matching fallback
                    if (intFieldIndex == 1) field.setInt(instance, hexColor);  // Your exact target RGB Hex!
                    intFieldIndex++;
                }
            }
            return instance;
        } catch (Exception e) {
            return MapColor.COLOR_BLACK;
        }
    }
}
