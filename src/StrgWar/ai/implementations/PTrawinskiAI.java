package StrgWar.ai.implementations;

import java.util.ArrayList;
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

public class PTrawinskiAI extends AbstractActor {
	public PTrawinskiAI(ICommandExecutor commandExecutor,
			IReadonlyMapProvider readonlyMapProvider, String name) {
		super(commandExecutor, readonlyMapProvider);

		_name = name;
		_map = readonlyMapProvider.GetReadOnlyMap();
	}

	@Override
	public void run() {
		_logger.log(Level.INFO, "PTrawinskiAI AI starts");
		boolean isInterrupted = false;

		while (!isInterrupted) {
			ArrayList<ReadonlyNode> myNodes = GetAllMyNodes();
			ArrayList<ReadonlyNode> opponentNodes = GetNotMineNodes();

			ReadonlyNode myNode = null;
			if (myNodes != null)
				myNode = GetRandomNode(myNodes);
			ReadonlyNode opponentNode = null;
			if (opponentNodes != null)
				opponentNode = GetRandomNode(opponentNodes);

			// wykonanie ruchu
			// _logger.log(Level.INFO, "units from " +
			// sender.GetMapElementName()
			// + " to " + receiver.GetMapElementName());

			if (myNode != null && opponentNode != null) {
				_commandExecutor.ExecuteCommand(this,
						new StartSendingUnits(myNode.GetMapElementName(),
								opponentNode.GetMapElementName()));
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				isInterrupted = true;
			}
		}

		_logger.log(Level.INFO, "PTrawinskiAI AI ends");
	}

	private ReadonlyNode GetRandomNode(ArrayList<ReadonlyNode> nodes) {
		int nodeNumber = 0;
		
		if(nodes.size() > 0) {
			nodeNumber = RandNumber(1, nodes.size());
			return nodes.get(nodeNumber - 1);
		}
		else
			return null;
	}

	private ArrayList<ReadonlyNode> GetAllMyNodes() {
		ArrayList<ReadonlyNode> nodes = new ArrayList<ReadonlyNode>();

		for (ReadonlyNode node : _map.Nodes) {
			if (node.GetOccupantName().compareTo(_name) == 0)
				nodes.add(node);
		}

		return nodes;
	}

	private ArrayList<ReadonlyNode> GetNotMineNodes() {
		ArrayList<ReadonlyNode> nodes = new ArrayList<ReadonlyNode>();

		for (ReadonlyNode node : _map.Nodes) {
			if (node.GetOccupantName().compareTo(_name) != 0)
				nodes.add(node);
		}

		return nodes;
	}

	private int RandNumber(int min, int max) {
		Random rand = new Random();
		int range = max - min + 1;

		return rand.nextInt(range) + min;
	}

	@Override
	public final String GetName() {
		return _name;
	}

	private final String _name;

	private final ReadonlyMap _map;
	private static final Logger _logger = Logger
			.getLogger(GameLogicExecutor.class.getName());
}