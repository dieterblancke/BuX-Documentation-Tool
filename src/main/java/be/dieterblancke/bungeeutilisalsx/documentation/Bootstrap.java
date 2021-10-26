package be.dieterblancke.bungeeutilisalsx.documentation;

import be.dieterblancke.bungeeutilisalsx.common.api.command.Command;
import be.dieterblancke.bungeeutilisalsx.common.api.command.ParentCommand;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Bootstrap {

    @SneakyThrows
    public static void main(String[] args) {
        final BungeeUtilisalsX bungeeUtilisalsX = new BungeeUtilisalsX();
        bungeeUtilisalsX.initialize();
        final CommandManager commandManager = (CommandManager) bungeeUtilisalsX.getCommandManager();

        final File file = new File("markdown/commands.md");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        final FileWriter fileWriter = new FileWriter(file);

        fileWriter.append("# Commands\n");
        fileWriter.append("""
                                
                All commands in this list can be enabled / disabled or changed. Each (sub)command name, alias, permission, ... can be changed to your liking.
                """);
        fileWriter.append("""
                * ( argument ) stands for a required argument
                * [ argument ] stands for an optional argument
                                
                """);

        for (Command command : commandManager.getCommands()) {
            printCommandAsMarkdown(
                    fileWriter,
                    command,
                    "## ",
                    "/"
            );

            if (command.getCommand() instanceof ParentCommand parentCommand) {
                for (Command subCommand : parentCommand.getSubCommands()) {
                    printCommandAsMarkdown(
                            fileWriter,
                            subCommand,
                            "### ",
                            "/" + command.getName() + " "
                    );

                    if (subCommand.getCommand() instanceof ParentCommand parentSubCommand) {
                        for (Command subSubCommand : parentSubCommand.getSubCommands()) {
                            printCommandAsMarkdown(
                                    fileWriter,
                                    subSubCommand,
                                    "#### ",
                                    "/" + command.getName() + " " + subCommand.getName() + " "
                            );
                        }
                    }
                }
            }
        }

        fileWriter.flush();
        fileWriter.close();
        System.exit(0);
    }

    @SneakyThrows
    private static void printCommandAsMarkdown(final FileWriter fileWriter,
                                               final Command command,
                                               final String headerPrefix,
                                               final String prefix) {
        fileWriter.append(headerPrefix + command.getName());
        fileWriter.append("\n\n");
        fileWriter.append("""
                | Command | Default Aliases | Permission |
                | --- | --- | --- |
                """);
        fileWriter.append(String.format(
                "| %s | %s | %s |\n",
                prefix + command.getName(),
                (command.getAliases().length == 0 ? "" : prefix)
                        + Arrays.stream(command.getAliases()).collect(Collectors.joining(", " + prefix)),
                command.getPermission()
        ));
        fileWriter.append("\n");
        fileWriter.append("**Usage: **" + command.getCommand().getUsage() + "\n");
        fileWriter.append("**Description: **" + command.getCommand().getDescription() + "\n");
        fileWriter.append("**Images**:" + getImageUrls(prefix + " " + command.getName()) + "\n");
    }

    private static String[] getImageUrls(final String command) {
        final String[] urls = switch (command.toLowerCase()) {
            default -> new String[0];
        };

        return Arrays.stream(urls)
                .map(url -> "![" + command + " images](" + url + ")")
                .toArray(String[]::new);
    }
}
