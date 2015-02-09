package StrgWar.ai.implementations;

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
	}

	@Override
	public void run() {
		_logger.log(Level.INFO, "HDmowska AI starts");
		boolean isInterrupted = false;

		while (!isInterrupted) {
			ReadonlyNode sender = null;
			ReadonlyNode receiver = null;

			sender = GetMyBiggestNode();

			if (GetNeutralNodesCount() > 0)
				receiver = GetClosestNeutralNode(sender);
			else {
				if (_lastAttackedNode != null
						&& _lastAttackedNode.GetOccupantName().compareTo(_name) != 0) {
					receiver = _lastAttackedNode;

					ReadonlyNode tmp = GetCloserAndSmallerOpponentNode(sender,
							receiver);
					if (tmp != null)
						receiver = tmp;
				} else {
					receiver = GetSmallestOpponentNode();
					if(receiver != null)
						sender = GetMyClosestNode(receiver);
				}
			}

			_lastAttackedNode = receiver;

			// wykonanie ruchu
			// _logger.log(Level.INFO, "units from " +
			// sender.GetMapElementName()
			// + " to " + receiver.GetMapElementName());

			if (sender != null && receiver != null) {
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

	private ReadonlyNode GetBiggestNeutralNode() {
		ReadonlyNode n = null;

		for (ReadonlyNode node : _map.Nodes) {
			if (node.GetOccupantName().compareTo("neutral") == 0) {
				if (n == null
						|| node.GetOccupantArmySize() > n.GetOccupantArmySize())
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

	private ReadonlyNode GetCloserAndSmallerOpponentNode(ReadonlyNode sender,
			ReadonlyNode currentReceiver) {
		ReadonlyNode n = null;

		for (ReadonlyNode node : _map.Nodes) {
			if (node.GetOccupantName().compareTo("neutral") != 0
					&& node.GetOccupantName().compareTo(_name) != 0) {
				if (sender.GetPosition().distance(node.GetPosition()) < sender
						.GetPosition().distance(currentReceiver.GetPosition())
						&& node.GetOccupantArmySize() < currentReceiver
								.GetOccupantArmySize())
					return node;
			}
		}

		return n;
	}

	private ReadonlyNode GetClosestOpponentNode(ReadonlyNode sender) {
		ReadonlyNode n = null;

		for (ReadonlyNode node : _map.Nodes) {
			if (node.GetOccupantName().compareTo("neutral") != 0
					&& node.GetOccupantName().compareTo(_name) != 0) {
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

	private int GetNeutralNodesCount() {
		int amount = 0;

		for (ReadonlyNode node : _map.Nodes) {
			if (node.GetOccupantName().compareTo("neutral") == 0)
				amount++;
		}

		return amount;
	}

	private int GetOpponentNodesCount() {
		int amount = 0;

		for (ReadonlyNode node : _map.Nodes) {
			if (node.GetOccupantName().compareTo(_name) != 0
					&& node.GetOccupantName().compareTo("neutral") != 0)
				amount++;
		}

		return amount;
	}

	private int GetMyNodesCount() {
		int amount = 0;

		for (ReadonlyNode node : _map.Nodes) {
			if (node.GetOccupantName().compareTo(_name) == 0)
				amount++;
		}

		return amount;
	}

	private int GetNodesCount() {
		return _map.Nodes.size();
	}

	@Override
	public final String GetName() {
		return _name;
	}

	private ReadonlyNode _lastAttackedNode = null;
	private final String _name;

	private final ReadonlyMap _map;
	private static final Logger _logger = Logger
			.getLogger(GameLogicExecutor.class.getName());
}