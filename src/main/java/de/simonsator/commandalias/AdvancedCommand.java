package de.simonsator.commandalias;

public class AdvancedCommand {
	public final String BYPASS_PERM;
	public final boolean ALLOW_MORE_ARGUMETNS;
	public final String COMMAND_BEGIN;
	public final String PERMISSION;
	public final String ALIAS;

	public AdvancedCommand(String commandBegin, String permission, String alias, boolean allowMoreArguments, String bypassPerm) {
		COMMAND_BEGIN = commandBegin;
		PERMISSION = permission;
		ALIAS = alias;
		ALLOW_MORE_ARGUMETNS = allowMoreArguments;
		BYPASS_PERM = bypassPerm;
	}
}
