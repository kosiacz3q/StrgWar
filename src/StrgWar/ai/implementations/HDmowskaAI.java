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

		// wysylanie na zmiane
		// z naszego najmniejszego do najwiekszego neutralnego
		// a nastepnie najmniejszego wezla przeciwnika
		while (!isInterrupted) {
			ReadonlyNode sender = null;
			int min = 100000;
			for (ReadonlyNode node : _map.Nodes) {
				if (node.GetOccupantArmySize() < min
						&& node.GetOccupantName().compareTo(_name) == 0) {
					sender = node;
					min = node.GetOccupantArmySize();
				}
			}

			ReadonlyNode receiver = null;
			if (moveCounter % 2 == 0) { // do neutralnego
				for (ReadonlyNode node : _map.Nodes) {
					if (node.GetOccupantName().compareTo("neutral") == 0)
						receiver = node;
				}
			} else {
				int max = 0;
				for (ReadonlyNode node : _map.Nodes) {
					if (node.GetOccupantArmySize() > max
							&& node.GetOccupantName().compareTo(_name) != 0) {
						receiver = node;
						max = node.GetOccupantArmySize();
					}
				}
			}

			if (sender == null) {
				for (ReadonlyNode node : _map.Nodes) {
					if (node.GetOccupantName().compareTo(_name) == 0) {
						sender = node;
						break;
					}
				}
			}

			if (receiver == null) {
				for (ReadonlyNode node : _map.Nodes) {
					if (node.GetOccupantName().compareTo(_name) != 0) {
						receiver = node;
						break;
					}
				}
			}

			// wykonanie ruchu
			// _logger.log(Level.INFO, "units from " +
			// sender.GetMapElementName()
			// + " to " + receiver.GetMapElementName());
			if (sender != null && receiver != null) {
				_commandExecutor.ExecuteCommand(this, new StartSendingUnits(
						sender.GetMapElementName(), // sk¹d
						receiver.GetMapElementName())); // dok¹d

				moveCounter++;
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				isInterrupted = true;
			}
		}

		_logger.log(Level.INFO, "HDmowska AI ends");
	}

	@Override
	public final String GetName() {
		return _name;
	}

	private final String _name;
	private int moveCounter = 0;

	private final ReadonlyMap _map;
	private static final Logger _logger = Logger
			.getLogger(GameLogicExecutor.class.getName());
}