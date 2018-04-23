package ShowtimeScripts.dead.STGreenDragons.task.fighting;

import ShowtimeScripts.dead.STGreenDragons.main.STGreenDragons;
import ShowtimeScripts.dead.STGreenDragons.main.Task;
import ShowtimeScripts.dead.STGreenDragons.misc.DataContainer;
import ShowtimeScripts.dead.STGreenDragons.misc.Walker;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Equipment;
import org.powerbot.script.rt4.Game;
import org.powerbot.script.rt4.Npc;

public class Combat extends Task{
	
	public Combat(ClientContext ctx, DataContainer data){
		super(ctx, data);
		this.setData(data);
	}
	
	private DataContainer data;
	
	public void setData(DataContainer data){
		this.data = data;
	}
	
	Tile bottomLeftGreenDragon = new Tile(3134, 3688);
	Tile topRightGreenDragon = new Tile(3168, 3717);
	
	Area greenDragonArea = new Area(bottomLeftGreenDragon, topRightGreenDragon);
	private int[] dragonIDs = {264, 260, 263, 262, 261};
	
	@Override
	public boolean activate(){
		if(greenDragonArea.contains(ctx.players.local().tile()) && ctx.inventory.select().id(data.getFoodID()).count() > 1 && data.getLootablesCount(ctx) < 26)
			return true;
		
		return false;
	}
	
	@Override
	public void execute(){
		
		
		if(ctx.npcs.select().within(4).id(dragonIDs).select(npc -> npc.animation() == 92).poll().valid()){
			Condition.wait(() -> ctx.groundItems.select().id(data.getLootablesListID()).within(8).poll().inViewport(), Random.nextInt(25, 40), 50);
			return;
		}
		
		//If dragon is interacting with me but I'm not interacting with him
		if(!ctx.players.local().interacting().valid() && ctx.npcs.select().id(dragonIDs).within(10).poll().interacting().equals(ctx.players.local())){
			for(int i : dragonIDs){
				if(ctx.npcs.select().within(10).id(i).poll().interacting().equals(ctx.players.local())){
					STGreenDragons.status = "Killing dragon...";
					Npc drag = ctx.npcs.select().id(i).poll();
					drag.interact("Attack", drag.name());
					Condition.wait(() -> ctx.players.local().interacting().valid() || ctx.groundItems.select().within(16).id(data.getLootablesListID()).poll().valid(), 30, 35);
				}
			}
		}
		
		
		if(!ctx.players.local().interacting().valid() && ctx.players.local().animation() == -1){
			
			STGreenDragons.status = "Looking for Target";
			Npc currDragon = ctx.npcs.select().id(dragonIDs).nearest().select(npc -> (!npc.interacting().valid() && npc.animation() == -1) || npc.interacting().equals(ctx.players.local())).poll();
			
			if(currDragon.tile().distanceTo(ctx.players.local().tile()) > 6 && !currDragon.inViewport()){
				STGreenDragons.status = "Walking to Target";
				Walker walker = new Walker(ctx);
				Tile[] dragonTile = {currDragon.tile()};
				walker.walkPath(dragonTile);
				int startingHealth = ctx.combat.health();
				Condition.wait(() -> ctx.players.local().interacting().valid() || !ctx.players.local().inMotion() || ctx.combat.health() != startingHealth || ctx.groundItems.select().within(16).id(data.getLootablesListID()).poll().valid() || currDragon.tile().distanceTo(ctx.players.local().tile()) < 4);
			}
			
			if(!currDragon.inViewport() && currDragon.tile().distanceTo(ctx.players.local().tile()) < 6){
				STGreenDragons.status = "Turning Camera";
				ctx.camera.turnTo(currDragon);
				Condition.wait(() -> currDragon.inViewport() || ctx.groundItems.select().within(16).id(data.getLootablesListID()).poll().valid());
			}
			
			if(currDragon.inViewport() && !ctx.players.local().interacting().valid() && !ctx.players.select().poll().interacting().equals(currDragon) && currDragon.animation() == -1){
				STGreenDragons.status = "Attacking Dragon...";
				currDragon.interact("Attack", "Green Dragon");
			}
			
			if(ctx.players.local().inMotion()){
				System.out.println("In wait -> Combat");
				Condition.wait(() -> ctx.players.local().interacting().valid() || ctx.players.local().inMotion() || ctx.groundItems.select().within(16).id(data.getLootablesListID()).poll().valid());
			}
			
		}
		
		
		//Activates special attack if needed
		if(checkSpecial(ctx, data) && ctx.players.local().interacting().valid()){
			
			STGreenDragons.status = "Activating Special...";
			
			if(data.getSpecialAttackID() != -1){
				if(ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND).id() != data.getSpecialAttackID()){
					ctx.inventory.select().id(data.getSpecialAttackID()).poll().interact("Wield");
					Condition.wait(() -> ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND).id() == data.getSpecialAttackID());
				}
			}
			
			ctx.game.tab(Game.Tab.ATTACK);
			Condition.wait(() -> ctx.game.tab(Game.Tab.ATTACK));
			ctx.widgets.widget(593).component(29).interact("Use");
			Condition.wait(() -> !ctx.combat.specialAttack());
			if(ctx.combat.specialPercentage() < 75){
				ctx.game.tab(Game.Tab.INVENTORY);
			}
		}
		
		
		if(ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND).id() != data.getPrimaryWeaponID() && !ctx.combat.specialAttack() && ctx.combat.specialPercentage() < 75){
			if(!ctx.game.tab(Game.Tab.INVENTORY)){
				ctx.game.tab(Game.Tab.INVENTORY);
			}
			Condition.wait(() -> !ctx.combat.specialAttack(), 30, 60);
			ctx.inventory.select().id(data.getPrimaryWeaponID()).poll().interact("Wield");
			
		}
		
		
	}
	
	private boolean checkSpecial(ClientContext ctx, DataContainer data){
		if(!data.isSpecialAttack()) return false;
		if(ctx.combat.specialPercentage() >= 75 && !ctx.combat.specialAttack()){
			if(Random.nextInt(1, 7) == 3){
				return true;
			}
		}
		return false;
	}
}
