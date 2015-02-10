package StrgWar.ai.implementations;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

import StrgWar.ai.AbstractActor;
import StrgWar.ai.GameLogicExecutor;
import StrgWar.ai.ICommandExecutor;
import StrgWar.ai.StartSendingUnits;
import StrgWar.map.readonly.IReadonlyMapProvider;
import StrgWar.map.readonly.ReadonlyMap;
import StrgWar.map.readonly.ReadonlyNode;

public class LKosiakAI extends AbstractActor
{
	public LKosiakAI(ICommandExecutor commandExecutor, IReadonlyMapProvider readonlyMapProvider, String name)
	{
		super(commandExecutor , readonlyMapProvider);
		
		_name = name;
		_map = readonlyMapProvider.GetReadOnlyMap();
	}
	
	@Override
	public void run()
	{
		_logger.log(Level.INFO, "LKosiak AI starts");
		boolean isInterrupted = false;
		
		
		ArrayList<ReadonlyNode> usedNodes = new ArrayList<ReadonlyNode>();
		ArrayList<ReadonlyNode> enemyNodes = new ArrayList<ReadonlyNode>();
		
		//PROSTE AI wysy³aj¹ce jednostki z naczego najwiêkszego miasta do najmniejszego nie naszego na mapie
		
		while (!isInterrupted)
		{
			
			enemyNodes.clear();
			///listujemy wrogie miasta
			for (ReadonlyNode target : _map.Nodes)
			{
				//sprawdzamy czy to nie s¹ przypadkiem nasze jednostki
				if (target.GetOccupantName().compareTo(_name) != 0 )
				{
					enemyNodes.add(target);
				}
			}
			
			//sortujemy rosnaco wzgledem ilosci jednostek
			enemyNodes.sort(new Comparator<ReadonlyNode>() {

				@Override
				public int compare(ReadonlyNode o1, ReadonlyNode o2)
				{
					return o1.GetOccupantArmySize() - o2.GetOccupantArmySize();
				}
			});

			
			usedNodes.clear();
			
			//wysylamy tyle ile trzeba zeby przejac
			for (ReadonlyNode target : enemyNodes)
			{
				int enemyCount = 2 * target.GetOccupantArmySize();
				
				
				for (ReadonlyNode node : _map.Nodes)
				{
					//szukamy naszego miasta
					if (usedNodes.indexOf(node) == -1 && node.GetOccupantName().compareTo(_name) == 0)
					{
						usedNodes.add(node);
						
						enemyCount -= node.GetOccupantArmySize();
						
						_commandExecutor.ExecuteCommand(this, new StartSendingUnits(
								node.GetMapElementName(), //sk¹d
								target.GetMapElementName())); //dok¹d
						
						if (enemyCount < 0)
							break;
					}
				}				
			}
			
			//wysy³amy jednostki 
			//_logger.log(Level.FINE, "units from " + nodeWithMaxOfOurUnits.GetMapElementName() + "(" + nodeWithMaxOfOurUnits.GetOccupantArmySize()  +")" + " to " + nodeWithMinCountOfEnemy.GetMapElementName() + "(" + nodeWithMinCountOfEnemy.GetOccupantArmySize() + ")");
		

			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				isInterrupted = true;
			}
		}
		
		_logger.log(Level.INFO, "LKosiak AI ends");
	}

	@Override
	public final String GetName()
	{
		return _name;
	}
	
	private final String _name;
	
	private final ReadonlyMap _map;
	private static final Logger _logger = Logger.getLogger( GameLogicExecutor.class.getName() );
}
