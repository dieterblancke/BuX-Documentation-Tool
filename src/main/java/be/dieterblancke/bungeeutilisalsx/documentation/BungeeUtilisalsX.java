package be.dieterblancke.bungeeutilisalsx.documentation;

import be.dieterblancke.bungeeutilisalsx.common.AbstractBungeeUtilisalsX;
import be.dieterblancke.bungeeutilisalsx.common.IBuXApi;
import be.dieterblancke.bungeeutilisalsx.common.IPluginDescription;
import be.dieterblancke.bungeeutilisalsx.common.ProxyOperationsApi;
import be.dieterblancke.bungeeutilisalsx.common.api.command.Command;
import be.dieterblancke.bungeeutilisalsx.common.api.utils.other.IProxyServer;
import be.dieterblancke.bungeeutilisalsx.common.api.utils.other.PluginInfo;
import be.dieterblancke.bungeeutilisalsx.common.api.utils.other.StaffUser;
import be.dieterblancke.bungeeutilisalsx.common.commands.CommandManager;
import net.md_5.bungee.api.chat.BaseComponent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class BungeeUtilisalsX extends AbstractBungeeUtilisalsX {

    private final CommandManager commandManager = new be.dieterblancke.bungeeutilisalsx.documentation.CommandManager();

    @Override
    protected IBuXApi createBuXApi() {
        return null;
    }

    @Override
    public CommandManager getCommandManager() {
        return commandManager;
    }

    @Override
    protected void loadDatabase() {

    }

    @Override
    protected void migrate() {

    }

    @Override
    protected void registerLanguages() {

    }

    @Override
    protected void registerListeners() {

    }

    @Override
    protected void registerPluginSupports() {

    }

    @Override
    public ProxyOperationsApi proxyOperations() {
        return new ProxyOperationsApi() {
            @Override
            public void registerCommand(Command command) {

            }

            @Override
            public void unregisterCommand(Command command) {

            }

            @Override
            public List<IProxyServer> getServers() {
                return new ArrayList<>();
            }

            @Override
            public IProxyServer getServerInfo(String s) {
                return null;
            }

            @Override
            public String getProxyIdentifier() {
                return null;
            }

            @Override
            public List<PluginInfo> getPlugins() {
                return null;
            }

            @Override
            public Optional<PluginInfo> getPlugin(String s) {
                return Optional.empty();
            }

            @Override
            public long getMaxPlayers() {
                return 0;
            }

            @Override
            public Object getMessageComponent(BaseComponent... baseComponents) {
                return null;
            }
        };
    }

    @Override
    protected void registerExecutors() {

    }

    @Override
    public File getDataFolder() {
        return new File("data");
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public List<StaffUser> getStaffMembers() {
        return null;
    }

    @Override
    public IPluginDescription getDescription() {
        return null;
    }

    @Override
    public Logger getLogger() {
        return Logger.getLogger("BuX");
    }

    @Override
    protected void registerMetrics() {

    }

    @Override
    public boolean isProtocolizeEnabled() {
        return true;
    }
}
