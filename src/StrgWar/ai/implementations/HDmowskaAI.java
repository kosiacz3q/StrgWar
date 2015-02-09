package StrgWar.ai.implementations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import StrgWar.ai.AbstractActor;
import StrgWar.ai.GameLogicExecutor;
import StrgWar.ai.ICommandExecutor;
import StrgWar.ai.StartSendingUnits;
import StrgWar.map.readonly.IReadonlyMapProvider;
import StrgWar.map.readonly.ReadonlyMap;
import StrgWar.map.readonly.ReadonlyNode;

public class HDmowskaAI extends AbstractActor {
	public HDmowskaAI(ICommandExecutor commandExecutor,
			IReadonlyMapProvider readonlyMapProvider, String name) {
		super(commandExecutor, readonlyMapProvider);

		_name = name;
		_map = readonlyMapProvider.GetReadOnlyMap();
		_alreadySending = new ArrayList<ReadonlyNode>();
	}

	@Override
	public void run() {
		_logger.log(Level.INFO, "HDmowska AI starts");
		boolean isInterrupted = false;

		while (!isInterrupted) {
			ReadonlyNode sender = null;
			ReadonlyNode receiver = null;

			if (_alreadySending != null) {
				Iterator<ReadonlyNode> i = _alreadySending.iterator();
				while (i.hasNext()) {
					ReadonlyNode node = i.next();
					if (node.GetOccupantName().compareTo(_name) != 0)
						i.remove();
				}
			}

			if (GetNeutralNodesCount() > 0) {
				sender = GetMyNodeWhichIsNotSending();
				if (sender != null)
					receiver = GetClosestNeutralNode(sender);
				else {
					sender = GetMyBiggestNode();
					if(sender != null)
						receiver = GetClosestNeutralNode(sender);
				}
			} else {
				receiver = GetSmallestOpponentNode();
				if (receiver != null) {
					sender = GetMyClosestNodeWhichIsNotSending(receiver);
					if (sender == null)
						sender = GetMyClosestNode(receiver);
				}
			}

			// wykonanie ruchu
			// _logger.log(Level.INFO, "units from " +
			// sender.GetMapElementName()
			// + " to " + receiver.GetMapElementName());

			if (sender != null && receiver != null) {
				_alreadySending.add(sender);
				_commandExecutor.ExecuteCommand(this,
						new StartSendingUnits(sender.GetMapElementName(),
								receiver.GetMapElementName()));
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				isInterrupted = true;
			}
		}

		_logger.log(Level.INFO, "HDmowska AI ends");
	}

	private ReadonlyNode GetSmallestOpponentNode() {
		ReadonlyNode n = null;

		for (ReadonlyNode node : _map.Nodes) {
			if (node.GetOccupantName().compareTo("neutral") != 0
					&& node.GetOccupantName().compareTo(_name) != 0) {
				if (n == null
						|| node.GetOccupantArmySize() < n.GetOccupantArmySize())
					n = node;
			}
		}

		return n;
	}

	private ReadonlyNode GetMyClosestNodeWhichIsNotSending(ReadonlyNode anyNode) {
		ReadonlyNode n = null;

		for (ReadonlyNode node : _map.Nodes) {
			if (node.GetOccupantName().compareTo(_name) == 0) {
				if (n == null
						|| node.GetPosition().distance(anyNode.GetPosition()) < n
								.GetPosition().distance(anyNode.GetPosition())
						&& !_alreadySending.contains(node))
					n = node;
			}
		}

		return n;
	}

	private ReadonlyNode GetMyClosestNode(ReadonlyNode anyNode) {
		ReadonlyNode n = null;

		for (ReadonlyNode node : _map.Nodes) {
			if (node.GetOccupantName().compareTo(_name) == 0) {
				if (n == null
						|| node.GetPosition().distance(anyNode.GetPosition()) < n
								.GetPosition().distance(anyNode.GetPosition()))
					n = node;
			}
		}

		return n;
	}

	private ReadonlyNode GetClosestNeutralNode(ReadonlyNode sender) {
		ReadonlyNode n = null;

		for (ReadonlyNode node : _map.Nodes) {
			if (node.GetOccupantName().compareTo("neutral") == 0) {
				if (n == null
						|| sender.GetPosition().distance(node.GetPosition()) < sender
								.GetPosition().distance(n.GetPosition()))
					n = node;
			}
		}

		return n;
	}

	private ReadonlyNode GetMyBiggestNode() {
		ReadonlyNode n = null;

		for (ReadonlyNode node : _map.Nodes) {
			if (node.GetOccupantName().compareTo(_name) == 0) {
				if (n == null
						|| node.GetOccupantArmySize() > n.GetOccupantArmySize())
					n = node;
			}
		}

		return n;
	}

	private ReadonlyNode GetMyNodeWhichIsNotSending() {
		for (ReadonlyNode node : _map.Nodes) {
			if (node.GetOccupantName().compareTo(_name) == 0
					&& !_alreadySending.contains(node))
				return node;
		}

		return null;
	}

	private int GetNeutralNodesCount() {
		int amount = 0;

		for (ReadonlyNode node : _map.Nodes) {
			if (node.GetOccupantName().compareTo("neutral") == 0)
				amount++;
		}

		return amount;
	}

	@Override
	public final String GetName() {
		return _name;
	}

	private ArrayList<ReadonlyNode> _alreadySending;
	private final String _name;

	private final ReadonlyMap _map;
	private static final Logger _logger = Logger
			.getLogger(GameLogicExecutor.class.getName());
}