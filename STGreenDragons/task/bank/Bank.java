package ShowtimeScripts.dead.STGreenDragons.task.bank;

import ShowtimeScripts.dead.STGreenDragons.main.STGreenDragons;
import ShowtimeScripts.dead.STGreenDragons.main.Task;
import ShowtimeScripts.dead.STGreenDragons.misc.DataContainer;
import ShowtimeScripts.dead.STGreenDragons.misc.Walker;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Equipment;
import org.powerbot.script.rt4.Game;

public class Bank extends Task{
	
	
	public Bank(ClientContext ctx, DataContainer data){
		super(ctx, data);
		this.setData(data);
	}
	
	private DataContainer data;
	
	private Tile innerBankBottomLeft = new Tile(3091, 3488);
	private Tile innerBankTopRight = new Tile(3098, 3498);
	private Area innerBankArea = new Area(innerBankBottomLeft, innerBankTopRight);
	
	private Tile bottomLeftEdgevilleBank = new Tile(3085, 3486);
	private Tile topRightEdgevilleBank = new Tile(3100, 3502);
	
	private Area edgevilleBankArea = new Area(bottomLeftEdgevilleBank, topRightEdgevilleBank);
	
	@Override
	public boolean activate(){
		if(edgevilleBankArea.contains(ctx.players.local().tile()) && !data.hasGloryEquipped(ctx, data)) return true;
		if(edgevilleBankArea.contains(ctx.players.local().tile()) && ctx.inventory.select().id(data.getFoodID()).count() < data.getStartingFoodAmount())
			return true;
		return false;
	}
	
	@Override
	public void execute(){
		
		STGreenDragons.status = "Banking";
		
		if(!ctx.bank.inViewport() && !innerBankArea.contains(ctx.players.local().tile())){
			Walker walker = new Walker(ctx);
			System.out.println("Walker");
			Tile[] innerTile = {new Tile(3092, 3493)};
			walker.walkPath(innerTile);
		}
		
		
		if(ctx.equipment.itemAt(Equipment.Slot.NECK).id() == data.amuletOfGlory[0]){
			ctx.game.tab(Game.Tab.EQUIPMENT);
			ctx.equipment.itemAt(Equipment.Slot.NECK).interact("Remove");
			Condition.wait(() -> ctx.equipment.itemAt(Equipment.Slot.NECK).id() != data.amuletOfGlory[0]);
			ctx.game.tab(Game.Tab.INVENTORY);
		}
		
		
		if(ctx.bank.opened()){
			
			if(ctx.bank.select().id(data.getFoodID()).count(true) < data.getStartingFoodAmount()){
				System.out.println("BANK_TASK1");
				ctx.controller.stop();
			}
			if(!data.hasGlory(ctx) && !data.hasGloryInventory(ctx, data) && !data.hasGloryEquipped(ctx, data)){
				System.out.println("BANK.TASK2");
				ctx.controller.stop();
			}
			
			for(int i : data.getLootablesListID()){
				int count = ctx.inventory.select().id(i).count();
				int value = data.getItemValueMap(i);
				STGreenDragons.goldBanked += count * value;
			}
			
			
			ctx.bank.depositAllExcept(data.getSpecialAttackID(), data.getFoodID());
			
			ctx.bank.withdraw(data.getFoodID(), data.getStartingFoodAmount() - ctx.inventory.select().id(data.getFoodID()).count());
			if(data.isSpecialAttack() && ctx.inventory.select().id(data.getSpecialAttackID()).count() == 0){
				ctx.bank.withdraw(data.getSpecialAttackID(), 1);
			}
			if(!(data.hasGloryEquipped(ctx, data) || data.hasGloryInventory(ctx, data))){
				ctx.bank.withdraw(data.gloryID(ctx, data), 1);
			}
			ctx.bank.close();
			
			
		}else{
			if(ctx.bank.inViewport()){
				ctx.bank.open();
			}else{
				ctx.camera.turnTo(ctx.bank.nearest());
			}
		}
		
		if(data.hasGloryInventory(ctx, data)){
			ctx.game.tab(Game.Tab.INVENTORY);
			ctx.inventory.select().id(data.gloryIDInventory(ctx, data)).poll().interact("Wear");
			Condition.wait(() -> data.hasGloryEquipped(ctx, data));
		}
		
		
	}
	
	
	public DataContainer getData(){
		return data;
	}
	
	public void setData(DataContainer data){
		this.data = data;
	}
	
}
