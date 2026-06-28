package better_smp.client.mixin;

import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Gui.class)
public interface OverlayClearMixin {
        @Accessor("toolHighlightTimer")
        void setToolHighlightTimer(int timer);
}
