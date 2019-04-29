package mod.amalgam.entity;

import java.util.UUID;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class EntityMachine extends EntityCreature {
	private EntityPlayer playerBeingFollowed;
	public EntityMachine(World world) {
		super(world);
	}
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		if (this.getPlayerUUIDBeingFollowed() != null) {
			compound.setUniqueId("playerBeingFollowed", this.getPlayerUUIDBeingFollowed());
		}
	}
    @Override
	public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
		this.setPlayerUUIDBeingFollowed(compound.getUniqueId("playerBeingFollowed"));
    }
	public void setPlayerUUIDBeingFollowed(UUID uuid) {
		this.playerBeingFollowed = this.world.getPlayerEntityByUUID(uuid);
	}
	public UUID getPlayerUUIDBeingFollowed() {
		if (this.playerBeingFollowed != null) {
			return this.playerBeingFollowed.getUniqueID();
		}
		else {
			return null;
		}
	}
	public void setPlayerBeingFollowed(EntityPlayer player) {
		this.playerBeingFollowed = player;
	}
	public EntityPlayer getPlayerBeingFollowed() {
		return this.playerBeingFollowed;
	}
	public void say(EntityPlayer player, String line) {
		player.sendMessage(new TextComponentString("<" + this.getName() + "> " + line));
	}
}
