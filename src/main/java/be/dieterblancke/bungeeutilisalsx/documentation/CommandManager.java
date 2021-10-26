package be.dieterblancke.bungeeutilisalsx.documentation;

import be.dieterblancke.bungeeutilisalsx.common.api.command.Command;
import be.dieterblancke.bungeeutilisalsx.common.api.command.CommandBuilder;
import be.dieterblancke.bungeeutilisalsx.common.api.command.CommandCall;
import com.dbsoftwares.configuration.api.ISection;

import java.util.List;

public class CommandManager extends be.dieterblancke.bungeeutilisalsx.common.commands.CommandManager {

    public List<Command> getCommands() {
        return commands;
    }

    @Override
    protected void registerCommand(String name, boolean enabled, ISection section, CommandCall call) {
        CommandBuilder commandBuilder = CommandBuilder.builder()
                .name(name)
                .fromSection(section)
                .enabled(true)
                .executable(call);
        this.buildCommand(name, commandBuilder);
    }

    @Override
    protected void registerCustomCommands() {

    }

    @Override
    protected void registerSlashServerCommands() {

    }
}
