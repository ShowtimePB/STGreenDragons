package ShowtimeScripts.dead.STGreenDragons.main;

import ShowtimeScripts.dead.STGreenDragons.gui.STGDSettingsGUI;
import ShowtimeScripts.dead.STGreenDragons.gui.STGreenDragonsGUI;
import ShowtimeScripts.dead.STGreenDragons.misc.DataContainer;
import ShowtimeScripts.dead.STGreenDragons.task.StrangerDanger;
import ShowtimeScripts.dead.STGreenDragons.task.bank.Bank;
import ShowtimeScripts.dead.STGreenDragons.task.bank.DeathBank;
import ShowtimeScripts.dead.STGreenDragons.task.deathrun.Danger;
import ShowtimeScripts.dead.STGreenDragons.task.deathrun.DeathTeleport;
import ShowtimeScripts.dead.STGreenDragons.task.deathrun.WalkToLumbyBank;
import ShowtimeScripts.dead.STGreenDragons.task.fighting.Combat;
import ShowtimeScripts.dead.STGreenDragons.task.fighting.Eat;
import ShowtimeScripts.dead.STGreenDragons.task.fighting.Loot;
import ShowtimeScripts.dead.STGreenDragons.task.travel.WalkToBank;
import ShowtimeScripts.dead.STGreenDragons.task.travel.WalkToDragons;
import ShowtimeScripts.dead.STGreenDragons.task.travel.WorldHop;
import org.powerbot.script.*;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.Equipment;
import org.powerbot.script.rt4.Game;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

@Script.Manifest(
		name = "STGreenDragons",
		description = "Kills Green Dragons in the Wilderness and Banks the Loot!",
		properties = "author=ShowtimeScripts; topic=1343313; client=4;")

public class STGreenDragons extends PollingScript<ClientContext> implements PaintListener, MessageListener{

	ArrayList<Task> taskList = new ArrayList<>();

	public static String status = "";
	public static boolean teleblocked = false;
	public static int goldBanked;
	private int healthXP;
	private int combatXP;
	private int deathCount;
	public static int hopCount;

	//The GUI Vars
	public static int foodIDStart;
	public static boolean lootBones;
	public static boolean lootHides;
	public static int foodAmtStart;
	public static int ringIDStart;
	public static boolean saveSettings;
	public static boolean readFromFile = false;


	public static STGreenDragonsGUI g = new STGreenDragonsGUI();
	public static STGDSettingsGUI g2 = new STGDSettingsGUI();

	@Override
	public void start(){
		DataContainer data = new DataContainer();
		data.setLootablesListID(new ArrayList<Integer>());
		STGreenDragons.status = "Initializing";

		readFromFile = false;

		try{

			String fileName = getStorageDirectory().getAbsolutePath() + "/stgreen_settings";
			if(new File(fileName).isFile()){
				readFromFile = true;
			}

		}catch(Exception e){
			e.printStackTrace();
		}

		//CONFIRMING IMPORT FROM FILE
		if(readFromFile){
			g2.setVisible(true);
			while(g2.isVisible()){
				Condition.sleep(20);
			}
		}


		//IMPORTING FROM FILE
		if(readFromFile){
			data.importSettingsFile(getStorageDirectory());

		}else{

			//IMPORTING FROM GUI
			g.setVisible(true);
			while(g.isVisible()){
				Condition.sleep(20);
			}

			if(lootBones) data.addLootables(536);
			if(lootHides) data.addLootables(1753);

			if(ringIDStart < -1){
				System.out.println("RING ID INPUT INVALID");
				ctx.controller.stop();
			}
			if(foodIDStart < 0){
				System.out.println("FOOD ID INPUT INVALID");
				ctx.controller.stop();
			}
			if(foodAmtStart < 1){
				System.out.println("FOOD AMT INPUT INVALID");
				ctx.controller.stop();
			}

			data.setRingID(ringIDStart);
			data.setFoodID(foodIDStart);
			data.setStartingFoodAmount(foodAmtStart);

			if(saveSettings) data.createSettingsFile(getStorageDirectory(), lootHides, lootBones);


		}

		//Init normal values

		int[] lootables = {1213, 1201, 13511, 12746, 1373, 1319, 1249, 1149, 2366, 1199, 207, 1185, 985, 987, 2363, 1615, 1617, 13511};
		for(int i : lootables){
			data.addLootables(i);
		}
		data.initItemValueMap();
		data.initSpecialAttack(ctx);
		ctx.game.tab(Game.Tab.INVENTORY);
		ctx.combat.autoRetaliate(true);
		ctx.game.tab(Game.Tab.INVENTORY);

		/*
		 * START PAINT
		 */

		data.initItemValueMap();
		data.initSpecialAttack(ctx);
		goldBanked = 0;
		deathCount = 0;
		hopCount = 0;
		healthXP = ctx.skills.experience(Constants.SKILLS_HITPOINTS);
		combatXP = ctx.skills.experience(Constants.SKILLS_ATTACK) + ctx.skills.experience(Constants.SKILLS_DEFENSE) + ctx.skills.experience(Constants.SKILLS_STRENGTH);

		/*
		 * END PAINT
		 */

		/*
		 * START TASKLIST
		 */

		taskList.add(new Danger(ctx, data));
		taskList.add(new StrangerDanger(ctx, data));
		taskList.add(new WalkToBank(ctx, data));
		taskList.add(new Eat(ctx, data));
		taskList.add(new Loot(ctx, data));
		taskList.add(new WorldHop(ctx, data));
		taskList.add(new DeathBank(ctx, data));
		taskList.add(new DeathTeleport(ctx, data));
		taskList.add(new WalkToLumbyBank(ctx, data));
		taskList.add(new Combat(ctx, data));
		taskList.add(new Bank(ctx, data));
		taskList.add(new WalkToDragons(ctx, data));

		/*
		 * END TASKLIST
		 */


		/*
		 * GETS IDS FROM PLAYER
		 */


		data.setCapeID(ctx.equipment.itemAt(Equipment.Slot.CAPE).id());
		data.setBootsID(ctx.equipment.itemAt(Equipment.Slot.FEET).id());
		data.setChestID(ctx.equipment.itemAt(Equipment.Slot.TORSO).id());
		data.setGlovesID(ctx.equipment.itemAt(Equipment.Slot.HANDS).id());
		data.setHelmID(ctx.equipment.itemAt(Equipment.Slot.HEAD).id());
		data.setLegsID(ctx.equipment.itemAt(Equipment.Slot.LEGS).id());
		data.setRingID(ctx.equipment.itemAt(Equipment.Slot.RING).id());
		data.setPrimaryWeaponID(ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND).id());

		/*
		 * END GET
		 */


	}

	@Override
	public void poll(){
		for(Task t : taskList){
			if(t.activate()){
				t.execute();
				break;
			}
		}
	}


	@Override
	public void messaged(MessageEvent messageEvent){

		String msg = messageEvent.text();

		if(msg.contains("block")){
			System.out.println("FUCK CUFCJCC");
			Danger FUCK = new Danger(ctx, null);
			STGreenDragons.teleblocked = true;
			FUCK.execute();
		}

		if(msg.contains("dead")){
			System.out.println("YOU DIED!!!");
			deathCount++;
			WorldHop wh = new WorldHop(ctx, null);
			wh.execute();
		}

	}

	@Override
	public void repaint(Graphics graphics){


		long milliseconds = this.getTotalRuntime();
		long seconds = (milliseconds / 1000) % 60;
		long minutes = ((milliseconds / 1000) / 60) % 60;
		long hours = ((milliseconds / 1000) / 60) / 60;

		int currHealthXP = ctx.skills.experience(Constants.SKILLS_HITPOINTS);
		int currCombatXP = ctx.skills.experience(Constants.SKILLS_ATTACK) + ctx.skills.experience(Constants.SKILLS_DEFENSE) + ctx.skills.experience(Constants.SKILLS_STRENGTH);

		Graphics2D g = (Graphics2D) graphics;

		g.setColor(new Color(0, 0, 0));
		g.fillRect(15, 230, 180, 100);


		g.setColor(new Color(255, 255, 255));
		g.drawRect(15, 230, 180, 100);

		g.drawString("Time: " + String.format("%02d:%02d:%02d", hours, minutes, seconds), 21, 245);
		g.drawString("Status: " + String.format("%s", status), 21, 258);
		g.drawString("Health XP: " + (currHealthXP - healthXP), 21, 271);
		g.drawString("Combat XP: " + (currCombatXP - combatXP), 21, 284);
		g.drawString("Gold Banked: " + goldBanked, 21, 297);
		g.drawString("Deaths: " + deathCount, 21, 310);
		g.drawString("Hops: " + hopCount, 21, 323);


		g.setFont(new Font(g.getFont().getName(), Font.PLAIN, 18));
		g.drawString("STGreenDragons", 17, 227);

	}

}
