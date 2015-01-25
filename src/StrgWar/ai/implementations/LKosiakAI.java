package StrgWar.ai.implementations;

import java.util.logging.Level;
import java.util.logging.Logger;

import StrgWar.ai.AbstractActor;
import StrgWar.ai.GameLogicExecutor;
import StrgWar.ai.ICommandExecutor;
import StrgWar.ai.StartSendingUnits;
import StrgWar.ai.StopSendingCommand;
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
		
		this.run();
	}
	
	@Override
	public void run()
	{
		boolean isInterrupted = true;
		
		//PROSTE AI wysy�aj�ce jednostki z naczego najwi�kszego miasta do najmniejszego nie naszego na mapie
		// zak�ada �e istnieje conamniej jedno nasze miasto i jedno przeciwnika
		while (isInterrupted)
		{	
			ReadonlyNode nodeWithMinCountOfEnemy = _map.nodes.get(0);
			
			for (ReadonlyNode node : _map.nodes)
			{
				//sprawdzamy czy to nie s� przypadkiem nasze jednostki
				if (nodeWithMinCountOfEnemy.GetOccupantName().compareTo(_name) != 0 )
					//pobieramy ilo�c jednostek z wierzcho�ka 
					if (nodeWithMinCountOfEnemy.GetOccupantArmySize() > node.GetOccupantArmySize())
					{
						nodeWithMinCountOfEnemy = node;
					}
			}
			
			ReadonlyNode nodeWithMaxOfOurUnits = _map.nodes.get(0);
			
			for (ReadonlyNode node : _map.nodes)
			{
				//szukamy naszego miasta
				if (nodeWithMaxOfOurUnits.GetOccupantName().compareTo(_name) == 0 )
					//pobieramy ilo�c jednostek z wierzcho�ka 
					if (nodeWithMaxOfOurUnits.GetOccupantArmySize() < node.GetOccupantArmySize())
					{
							nodeWithMaxOfOurUnits = node;
					}
			}
			
			
			//wysy�amy jednostki 
			_logger.log(Level.INFO, "units from " + nodeWithMaxOfOurUnits.GetMapElementName() + " to " + nodeWithMinCountOfEnemy.GetMapElementName());
			_commandExecutor.ExecuteCommand(this, new StartSendingUnits(
													nodeWithMaxOfOurUnits.GetMapElementName(), //sk�d
													nodeWithMinCountOfEnemy.GetMapElementName())); //dok�d

			//mo�emy tak�e ewentualnie kaza� przesta� wysy�a� jednostki
			//_commandExecutor.ExecuteCommand(this, new StopSendingCommand(nodeWithMaxOfOurUnits.GetMapElementName()));

			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				isInterrupted = true;
			}
		}
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
