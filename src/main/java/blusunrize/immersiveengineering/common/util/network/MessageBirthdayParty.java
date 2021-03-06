package blusunrize.immersiveengineering.common.util.network;

import blusunrize.immersiveengineering.ImmersiveEngineering;
import blusunrize.immersiveengineering.common.util.Utils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageBirthdayParty implements IMessage
{
	int entityId;
	public MessageBirthdayParty(EntityLivingBase entity)
	{
		this.entityId = entity.getEntityId();
	}
	public MessageBirthdayParty()
	{
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.entityId = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.entityId);
	}

	public static class HandlerClient implements IMessageHandler<MessageBirthdayParty, IMessage>
	{
		@Override
		public IMessage onMessage(MessageBirthdayParty message, MessageContext ctx)
		{
			World world = ImmersiveEngineering.proxy.getClientWorld();
			if(world!=null)
			{
				Entity entity = world.getEntityByID(message.entityId);
				if(entity!=null&&entity instanceof EntityLivingBase)
				{
					world.makeFireworks(entity.posX, entity.posY, entity.posZ, 0, 0, 0, Utils.getRandomFireworkExplosion(Utils.RAND, 4));
					entity.getEntityData().setBoolean("headshot", true);
				}
			}
			return null;
		}
	}
}