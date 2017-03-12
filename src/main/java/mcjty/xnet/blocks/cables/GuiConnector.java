package mcjty.xnet.blocks.cables;

import mcjty.lib.container.EmptyContainer;
import mcjty.lib.container.GenericGuiContainer;
import mcjty.lib.gui.Window;
import mcjty.lib.gui.layout.HorizontalLayout;
import mcjty.lib.gui.layout.VerticalLayout;
import mcjty.lib.gui.widgets.Label;
import mcjty.lib.gui.widgets.Panel;
import mcjty.lib.gui.widgets.TextField;
import mcjty.lib.network.Argument;
import mcjty.xnet.XNet;
import mcjty.xnet.gui.GuiProxy;
import mcjty.xnet.network.XNetMessages;

import java.awt.*;

public class GuiConnector extends GenericGuiContainer<ConnectorTileEntity> {

    public static final int WIDTH = 220;
    public static final int HEIGHT = 30;

    private TextField nameField;

    public GuiConnector(AdvancedConnectorTileEntity te, EmptyContainer container) {
        this((ConnectorTileEntity) te, container);
    }

    public GuiConnector(ConnectorTileEntity tileEntity, EmptyContainer container) {
        super(XNet.instance, XNetMessages.INSTANCE, tileEntity, container, GuiProxy.GUI_MANUAL_XNET, "connector");

        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    public void initGui() {
        super.initGui();

        Panel toplevel = new Panel(mc, this).setFilledRectThickness(2).setLayout(new VerticalLayout());

        nameField = new TextField(mc, this).setTooltips("Set the name of this connector").addTextEvent((parent, newText) -> updateName());
        nameField.setText(tileEntity.getConnectorName());

        Panel bottomPanel = new Panel(mc, this).setLayout(new HorizontalLayout()).
                addChild(new Label(mc, this).setText("Name:")).addChild(nameField);
        toplevel.addChild(bottomPanel);

        toplevel.setBounds(new Rectangle(guiLeft, guiTop, WIDTH, HEIGHT));
        window = new Window(this, toplevel);
    }

    private void updateName() {
        sendServerCommand(XNetMessages.INSTANCE, ConnectorTileEntity.CMD_SETNAME, new Argument("name", nameField.getText()));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawWindow();
    }
}
