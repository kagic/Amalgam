package mod.akrivus.amalgam.gem;

import mod.akrivus.kagic.entity.EntityGem;

public interface IFusion {
	public EntityGem minorComponent = null;
	public EntityGem majorComponent = null;
	public void OnFuse(EntityGem passive, EntityGem fighter);
	public boolean CanFuse();
}
