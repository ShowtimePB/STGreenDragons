package ShowtimeScripts.dead.STGreenDragons.misc;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Equipment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DataContainer{
	
	private int primaryWeaponID = -1;
	private int specialAttackID = -1;
	private final int anti_dragonID = 1540;
	private int helmID = -1;
	private int chestID = -1;
	private int legsID = -1;
	private int capeID = -1;
	private int bootsID = -1;
	private int glovesID = -1;
	private int ringID = -1;
	
	private int[] specialAttackIDs = {5698};
	
	public int[] getSpecialAttackIDs(){
		return specialAttackIDs;
	}
	
	public void initSpecialAttack(ClientContext ctx){
		for(int i : this.getSpecialAttackIDs()){
			if(ctx.inventory.select().id(i).count() > 0){
				this.setSpecialAttack(true);
				this.setSpecialAttackID(i);
				return;
			}
		}
	}
	
	HashMap<Integer, Integer> itemValueMap = new HashMap<>();
	
	public void addToItemValueMap(int key, int value){
		itemValueMap.put(key, value);
	}
	
	public int getItemValueMap(int key){
		return itemValueMap.get(key);
	}
	
	public void initItemValueMap(){
		HashMap<Integer, Integer> map = new HashMap<>();
		for(int i : this.getLootablesListID()){
			map.put(i, 0);
		}
		STRepo.ST.api.STai.amplified_api.ClientContext.getGEValues(map);
	}
	
	
	public int[] getItemIDArray(){
		int[] array = {this.getPrimaryWeaponID(), this.getAnti_dragonID(), this.getHelmID(), this.getChestID(), this.getLegsID(), this.getCapeID(), this.getGlovesID(), this.getBootsID(), this.getRingID()};
		
		return array;
	}
	
	
	private boolean specialAttack = true;
	private int foodID = -1;
	private int healthPctEat = 60;
	private int startingFoodAmount = -1;
	public ArrayList<Integer> lootablesListID = new ArrayList<>();
	
	public final int[] amuletOfGlory = {1704, 1706, 1708, 1710, 1712, 11976, 11978};
	
	public int getHealthPctEat(){
		return healthPctEat;
	}
	
	public void setHealthPctEat(int healthPctEat){
		this.healthPctEat = healthPctEat;
	}
	
	public int[] getLootablesListID(){
		
		int size = lootablesListID.size();
		int[] returnList = new int[size];
		for(int i = 0; i < size; i++){
			returnList[i] = lootablesListID.get(i);
		}
		
		
		return returnList;
	}
	
	public void setLootablesListID(ArrayList<Integer> lootablesListID){
		this.lootablesListID = lootablesListID;
	}
	
	public void addLootables(int l){
		this.lootablesListID.add(l);
	}
	
	public int getFoodID(){
		return this.foodID;
	}
	
	public void setFoodID(int foodID){
		this.foodID = foodID;
	}
	
	public int getStartingFoodAmount(){
		return startingFoodAmount;
	}
	
	public void setStartingFoodAmount(int startingFoodAmount){
		this.startingFoodAmount = startingFoodAmount;
	}
	
	public DataContainer(){

	}
	
	public boolean isSpecialAttack(){
		return specialAttack;
	}
	
	public void setSpecialAttack(boolean specialAttack){
		this.specialAttack = specialAttack;
	}
	
	public int getSpecialAttackID(){
		return specialAttackID;
	}
	
	public void setSpecialAttackID(int specialAttackID){
		this.specialAttackID = specialAttackID;
	}
	
	public int getLootablesCount(ClientContext ctx){
		int lootables = 0;
		for(int i : lootablesListID){
			lootables += ctx.inventory.select().id(i).count();
		}
		return lootables;
	}
	
	public boolean hasGlory(ClientContext ctx){
		
		for(int i = 6; i > 0; i--){
			if(ctx.bank.select().id(amuletOfGlory[i]).count(true) > 0) return true;
			System.out.println("Checking for glory " + i);
		}
		
		return false;
	}
	
	public boolean correctInventory(ClientContext ctx){
		if(!(ctx.inventory.select().id(this.getFoodID()).count() == this.getStartingFoodAmount())) return false;
		if(this.isSpecialAttack()){
			if(ctx.inventory.select().id(this.getSpecialAttackID()).count() == 1) return false;
		}
		if(!hasGlory(ctx)) return false;
		return true;
	}
	
	
	public int gloryID(ClientContext ctx, DataContainer data){
		for(int i = 6; i > 0; i--){
			if(ctx.bank.select().id(data.amuletOfGlory[i]).count(true) > 0) return data.amuletOfGlory[i];
		}
		return 0;
	}
	
	public int gloryIDInventory(ClientContext ctx, DataContainer data){
		for(int i = 6; i > 0; i--){
			if(ctx.inventory.select().id(data.amuletOfGlory[i]).count() > 0) return data.amuletOfGlory[i];
		}
		return 0;
	}
	
	public boolean hasGloryEquipped(ClientContext ctx, DataContainer data){
		for(int i = 1; i < 7; i++){
			if(ctx.equipment.itemAt(Equipment.Slot.NECK).id() == data.amuletOfGlory[i]) return true;
		}
		return false;
	}
	
	public boolean hasGloryInventory(ClientContext ctx, DataContainer data){
		for(int i = 1; i < 7; i++){
			if(ctx.inventory.select().id(data.amuletOfGlory[i]).count() > 0) return true;
		}
		return false;
	}
	
	public int getPrimaryWeaponID(){
		return primaryWeaponID;
	}
	
	public void setPrimaryWeaponID(int primaryWeaponID){
		this.primaryWeaponID = primaryWeaponID;
	}
	
	public int getAnti_dragonID(){
		return anti_dragonID;
	}
	
	
	public int getHelmID(){
		return helmID;
	}
	
	public void setHelmID(int helmID){
		this.helmID = helmID;
	}
	
	public int getChestID(){
		return chestID;
	}
	
	public void setChestID(int chestID){
		this.chestID = chestID;
	}
	
	public int getLegsID(){
		return legsID;
	}
	
	public void setLegsID(int legsID){
		this.legsID = legsID;
	}
	
	public int getCapeID(){
		return capeID;
	}
	
	public void setCapeID(int capeID){
		this.capeID = capeID;
	}
	
	public int getBootsID(){
		return bootsID;
	}
	
	public void setBootsID(int bootsID){
		this.bootsID = bootsID;
	}
	
	public int getGlovesID(){
		return glovesID;
	}
	
	public void setGlovesID(int glovesID){
		this.glovesID = glovesID;
	}
	
	public int getRingID(){
		return ringID;
	}
	
	public void setRingID(int ringID){
		this.ringID = ringID;
	}
	
	public boolean correctEquip(ClientContext ctx, DataContainer data){
		
		
		int[] equippedArray = {
				ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND).id(),
				ctx.equipment.itemAt(Equipment.Slot.OFF_HAND).id(),
				ctx.equipment.itemAt(Equipment.Slot.HEAD).id(),
				ctx.equipment.itemAt(Equipment.Slot.TORSO).id(),
				ctx.equipment.itemAt(Equipment.Slot.LEGS).id(),
				ctx.equipment.itemAt(Equipment.Slot.CAPE).id(),
				ctx.equipment.itemAt(Equipment.Slot.HANDS).id(),
				ctx.equipment.itemAt(Equipment.Slot.FEET).id(),
				ctx.equipment.itemAt(Equipment.Slot.RING).id()};
		
		int[] fetchedArray = this.getItemIDArray();
		
		for(int i = 0; i < 9; i++){
			if(fetchedArray[i] != equippedArray[i]) return false;
		}
		return true;
	}
	
	public boolean correctBagEquip(ClientContext ctx){
		
		
		for(int i : this.getItemIDArray()){
			if(((ctx.inventory.select().id(i).count() == 0) && (i != -1))){
				
				System.out.println(i + " is the item id, -> is the count " + ctx.inventory.select().id(i).count());
				return false;
				
			}
		}
		return true;
	}
	
	public void importSettingsFile(File dir){
		
		String fileName = dir.getAbsolutePath() + "/stgreen_settings";
		File f = new File(fileName);
		
		try{
			Scanner s = new Scanner(f);
			String line = s.nextLine();
			String[] splitLine = line.split(";");
			
			if(splitLine[0].equalsIgnoreCase("true")){
				this.addLootables(1753);
			}
			
			if(splitLine[1].equalsIgnoreCase("true")){
				this.addLootables(536);
			}
			
			this.setFoodID(Integer.parseInt(splitLine[2]));
			this.setStartingFoodAmount(Integer.parseInt(splitLine[3]));
			this.setRingID(Integer.parseInt(splitLine[4]));
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	
	public void createSettingsFile(File dir, boolean hides, boolean bones){
		
		System.out.println("Create Settings File");
		String fileName = dir.getAbsolutePath() + "/stgreen_settings";
		File f = new File(fileName);
		
		try{
			PrintWriter pw = new PrintWriter(f);
			
			//Order of items: Loot Hides(boolean), Loot Bones(boolean), Food ID(int), Food Amt(int), Ring ID(int)
			
			pw.print(hides);
			pw.print(";");
			pw.print(bones);
			pw.print(";");
			pw.print(this.getFoodID());
			pw.print(";");
			pw.print(this.getStartingFoodAmount());
			pw.print(";");
			pw.print(this.getRingID());
			
			pw.close();
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
		
	}
	
	
}
