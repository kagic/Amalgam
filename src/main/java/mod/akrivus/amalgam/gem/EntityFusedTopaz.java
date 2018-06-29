package mod.akrivus.amalgam.gem;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityTopaz;
import net.minecraft.world.World;

public class EntityFusedTopaz extends EntityTopaz {
	public EntityTopaz gem1;
	public EntityTopaz gem2;
	public EntityFusedTopaz(World worldIn) {
		super(worldIn);
	}
	public boolean isFusion() {
		return true;
	}
	public void OnFuse(EntityGem passive, EntityGem fighter) {
		
	}
	public boolean CanFuse() {
		return false;
	}
	
}
