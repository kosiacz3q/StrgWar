package StrgWar.ai.implementations;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import StrgWar.ai.AbstractActor;
import StrgWar.ai.GameLogicExecutor;
import StrgWar.ai.ICommandExecutor;
import StrgWar.ai.StartSendingUnits;
import StrgWar.map.readonly.IReadonlyMapProvider;
import StrgWar.map.readonly.ReadonlyMap;
import StrgWar.map.readonly.ReadonlyNode;

public class KSzczepanskiAI extends AbstractActor {
	public KSzczepanskiAI(ICommandExecutor commandExecutor, IReadonlyMapProvider readonlyMapProvider, String name) {
		super(commandExecutor , readonlyMapProvider);
		
		_name = name;
		_map = readonlyMapProvider.GetReadOnlyMap();
	}
	
	@Override
	public void run() {
		_logger.log(Level.INFO, "KSzczepanski AI starts");
		boolean isInterrupted = false;
		Random rand = new Random();
		
		//Sending units from our strongest cell to random other, weaker than ours
		
		while (!isInterrupted) {				
			ReadonlyNode source = _map.Nodes.get(0);
			
			for (ReadonlyNode node : _map.Nodes) {
				if (node.GetOccupantName().compareTo(_name) == 0)
					if (source.GetOccupantArmySize() < node.GetOccupantArmySize()
						|| source.GetOccupantName().compareTo(_name) != 0) {
						source = node;
					}
			}
			
			ReadonlyNode target = _map.Nodes.get(0);
			
			while(target.GetOccupantName().compareTo(_name) == 0 || source.GetOccupantArmySize() < target.GetOccupantArmySize()) {
				target = _map.Nodes.get(rand.nextInt(_map.Nodes.size()));
			}
			
			_commandExecutor.ExecuteCommand(this, new StartSendingUnits(
					source.GetMapElementName(),
					target.GetMapElementName()));

			try {
				Thread.sleep(100);
			}
			catch (InterruptedException e) {
				isInterrupted = true;
			}
		}
		
		_logger.log(Level.INFO, "KSzczepanski AI ends");
	}

	@Override
	public final String GetName() {
		return _name;
	}
	
	private final String _name;
	
	private final ReadonlyMap _map;
	private static final Logger _logger = Logger.getLogger( GameLogicExecutor.class.getName() );
}
