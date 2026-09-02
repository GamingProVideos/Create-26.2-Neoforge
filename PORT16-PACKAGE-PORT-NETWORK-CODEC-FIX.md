# Port 16 - Package Port network codec fix

Minecraft 26.2 / NeoForge 26.2 rejects `ByteBufCodecs.registry(...)` when it is used with a custom registry that was not declared syncable.

The package-port target packet previously serialized `create:package_port_target_type` through registry integer IDs. Entering a world containing/triggering a package port caused:

```text
Failed to encode packet serverbound/create:place_package_port
Cannot use ID syncing for non-synced built-in registry: create:package_port_target_type
```

Port 16 serializes Create package-port target types by their stable `Identifier` (`create:chain_conveyor`, `create:train_station`, etc.) and then dispatches to the existing target payload codec.

The same unsafe registry-ID pattern in `ItemAttribute.PACKET_CODEC` was also converted to identifier-based networking to prevent the equivalent failure when filter/item-attribute packets are used.

No registry layout or saved-data codec was changed.
