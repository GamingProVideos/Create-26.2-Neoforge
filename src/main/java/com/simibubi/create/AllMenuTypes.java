package com.simibubi.create;

import com.simibubi.create.api.registry.CreateRegistries;
import com.simibubi.create.content.equipment.blueprint.BlueprintEntity.BlueprintSection;
import com.simibubi.create.content.equipment.blueprint.BlueprintMenu;
import com.simibubi.create.content.equipment.toolbox.ToolboxBlockEntity;
import com.simibubi.create.content.equipment.toolbox.ToolboxMenu;
import com.simibubi.create.content.logistics.factoryBoard.FactoryPanelSetItemMenu;
import com.simibubi.create.content.logistics.factoryBoard.ServerFactoryPanelBehaviour;
import com.simibubi.create.content.logistics.filter.AttributeFilterMenu;
import com.simibubi.create.content.logistics.filter.FilterMenu;
import com.simibubi.create.content.logistics.filter.PackageFilterMenu;
import com.simibubi.create.content.logistics.packagePort.PackagePortBlockEntity;
import com.simibubi.create.content.logistics.packagePort.PackagePortMenu;
import com.simibubi.create.content.logistics.redstoneRequester.RedstoneRequesterBlockEntity;
import com.simibubi.create.content.logistics.redstoneRequester.RedstoneRequesterMenu;
import com.simibubi.create.content.logistics.stockTicker.StockKeeperCategoryMenu;
import com.simibubi.create.content.logistics.stockTicker.StockKeeperRequestMenu;
import com.simibubi.create.content.logistics.stockTicker.StockTickerBlockEntity;
import com.simibubi.create.content.redstone.link.controller.LinkedControllerMenu;
import com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity;
import com.simibubi.create.content.schematics.cannon.SchematicannonMenu;
import com.simibubi.create.content.schematics.table.SchematicTableBlockEntity;
import com.simibubi.create.content.schematics.table.SchematicTableMenu;
import com.simibubi.create.content.trains.schedule.ScheduleMenu;
import com.simibubi.create.foundation.gui.menu.MenuType;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import static com.simibubi.create.Create.MOD_ID;

public class AllMenuTypes {
    public static final MenuType<ItemStack> SCHEDULE = register("schedule", ScheduleMenu::new);
    public static final MenuType<ItemStack> LINKED_CONTROLLER = register(
        "linked_controller",
        LinkedControllerMenu::new
    );
    public static final MenuType<ItemStack> FILTER = register("filter", FilterMenu::new);
    public static final MenuType<ItemStack> ATTRIBUTE_FILTER = register("attribute_filter", AttributeFilterMenu::new);
    public static final MenuType<ItemStack> PACKAGE_FILTER = register("package_filter", PackageFilterMenu::new);
    public static final MenuType<RedstoneRequesterBlockEntity> REDSTONE_REQUESTER = register(
        "redstone_requester",
        RedstoneRequesterMenu::new
    );
    public static final MenuType<StockTickerBlockEntity> STOCK_KEEPER_CATEGORY = register(
        "stock_keeper_category",
        StockKeeperCategoryMenu::new
    );
    public static final MenuType<StockTickerBlockEntity> STOCK_KEEPER_REQUEST = register(
        "stock_keeper_request",
        StockKeeperRequestMenu::new
    );
    public static final MenuType<PackagePortBlockEntity> PACKAGE_PORT = register("package_port", PackagePortMenu::new);
    public static final MenuType<ServerFactoryPanelBehaviour> FACTORY_PANEL_SET_ITEM = register(
        "factory_panel_set_item",
        FactoryPanelSetItemMenu::new
    );
    public static final MenuType<BlueprintSection> CRAFTING_BLUEPRINT = register(
        "crafting_blueprint",
        BlueprintMenu::new
    );
    public static final MenuType<ToolboxBlockEntity> TOOLBOX = register("toolbox", ToolboxMenu::new);
    public static final MenuType<SchematicTableBlockEntity> SCHEMATIC_TABLE = register(
        "schematic_table",
        SchematicTableMenu::new
    );
    public static final MenuType<SchematicannonBlockEntity> SCHEMATICANNON = register(
        "schematicannon",
        SchematicannonMenu::new
    );

    public static <T> MenuType<T> register(String name, MenuType<T> type) {
        return Registry.register(CreateRegistries.MENU_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, name), type);
    }

    public static void register() {
    }
}
