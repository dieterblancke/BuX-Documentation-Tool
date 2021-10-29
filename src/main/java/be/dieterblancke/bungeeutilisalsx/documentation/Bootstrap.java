package be.dieterblancke.bungeeutilisalsx.documentation;

import be.dieterblancke.bungeeutilisalsx.common.api.command.Command;
import be.dieterblancke.bungeeutilisalsx.common.api.command.ParentCommand;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Bootstrap {

    private static JsonObject commandImages;

    @SneakyThrows
    public static void main(String[] args) {
        commandImages = new Gson().fromJson(CharStreams.toString(
                new InputStreamReader(Bootstrap.class.getResourceAsStream("/command_images.json"))
        ), JsonObject.class);

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
                Each message can also be changed as you please!
                                
                The most obvious commands don't have screenshots included in order to limit the amount of images,
                you can also test everything on my test server: test.dieterblancke.xyz!
                """);
        fileWriter.append("""
                * ( argument ) stands for a required argument
                * [ argument ] stands for an optional argument
                * < argument > stands for a required argument based on a configuration variable
                                
                """);

        fileWriter.append("## Table of Contents\n");
        fileWriter.append(createTableOfContents(commandManager.getCommands(), "/"));
        fileWriter.append("\n");

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
        fileWriter.append(headerPrefix + prefix + command.getName());
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
        fileWriter.append("**Usage:** " + command.getCommand().getUsage() + "\n\n");
        fileWriter.append("**Description:** " + command.getCommand().getDescription() + "\n\n");

        final String images = getImageUrls(prefix + command.getName());

        if (!images.isEmpty()) {
            fileWriter.append("**Images:** \n\n" + images + "\n\n");
        }
    }

    private static String getImageUrls(final String command) {
        if (!commandImages.has(command)) {
            return "";
        }
        final StringBuilder builder = new StringBuilder("<div class=\"imagelist\">\n");
        final JsonArray images = commandImages.getAsJsonArray(command);

        for (JsonElement el : images) {
            builder.append("\t<img src=\"")
                    .append(el.getAsString())
                    .append("\" alt=\"")
                    .append(command)
                    .append(" images\"/>\n");
        }

        builder.append("</div>");

        return builder.toString();
    }

    private static String createTableOfContents(final List<Command> commands, final String prefix) {
        final StringBuilder builder = new StringBuilder();

        for (Command command : commands) {
            builder.append("- [" + prefix + command.getName() + "](#" + prefix.substring(1).replace(" ", "-") + command.getName() + ")\n");

            if (command.getCommand() instanceof ParentCommand) {
                builder.append(createTableOfContents(((ParentCommand) command.getCommand()).getSubCommands(), prefix + command.getName() + " "));
            }
        }
        return builder.toString();
    }
}
