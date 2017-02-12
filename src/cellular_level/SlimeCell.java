package cellular_level;

import java.util.ArrayList;
import java.util.List;


import data_structures.CellData;
import data_structures.PatchName;
import javafx.scene.paint.Color;
import patch_level.Patch;
import patch_level.SlimePatch;
import util.Location;

public class SlimeCell extends Cell {
	private static final Color SLIME_COLOR = Color.GREENYELLOW;

	public SlimeCell(){
		this(0,0);
	}
	
	public SlimeCell(int row, int col){
		this(0, 0, SLIME_COLOR);
	}
	
	public SlimeCell(int row, int col, Color c){
		super(row, col, c);
	}
	
	
	@Override
	public Cell createCopy() {
		SlimeCell copy = new SlimeCell();
		copy.basicCopy(this);
		return copy;
	}

	@Override
	public List<Cell> update(CellData data) {
		List<Cell> toRet = new ArrayList<Cell>();
		moveTowardsSlime(data);
		toRet.add(this);
		return toRet;
	}
	
	private ArrayList<SlimePatch> getSlimePatches(CellData data){
		Patch[][] patches = data.getCurrentPatchesCopy();
		ArrayList<SlimePatch> slime = new ArrayList<SlimePatch>();
		for(int i=0; i<patches.length; i++){
			for(int j=0; j<patches[0].length; j++){
				if(patches[i][j].getMyPatchType()==PatchName.SLIME_PATCH){
					slime.add((SlimePatch) patches[i][j]);
				}
			}
		}
		return slime;
	}
	
	//Takes in an ArrayList of SlimePatches on board
	private void moveTowardsSlime(CellData data){
		ArrayList<SlimePatch> slime = getSlimePatches(data);
		if(slime.size()==0){return;}
		Location target = getTargetLocation(slime);
		if(target==null){return;}
		Location newSpot = spotNearTarget(target, data);
		if(newSpot!=null){
			this.setMyLocation(newSpot);
		}
		
	}
	
	private Location getTargetLocation(ArrayList<SlimePatch> slime){
		Location newSpot=null;
		int maxChem = 0;
		double minDist = 1000; //random large number
		for(SlimePatch spot: slime){
			if(greaterConcentration(maxChem, spot) || 
					equalButCloser(maxChem, minDist, spot)){
				newSpot = spot.getMyLocation();
				maxChem = spot.getConcentration();
				minDist = this.getDistance(spot);
			}
		}
		return newSpot;
	}
	
	private Location spotNearTarget(Location target, CellData data){
		ArrayList<Location> neighbors = new ArrayList<Location>(data.getAvailableNeighbors(this));
		if(neighbors.size() == 0){return null;}
		Location toRet = neighbors.get(0);
		for(Location loc: neighbors){
			if(loc.distance(target) < toRet.distance(target)){
				toRet = loc;
			}
		}
		return toRet;
	}
	
	private boolean greaterConcentration(int maxChem, SlimePatch spot){
		return spot.getConcentration()>maxChem;
	}
	
	private boolean equalConcentration(int maxChem, SlimePatch spot){
		return spot.getConcentration()==maxChem;
	}
	
	private boolean equalButCloser(int maxChem, double minDistance, SlimePatch spot){
		return equalConcentration(maxChem, spot) && 
				this.getDistance(spot)<minDistance;
	}
	
	
}
