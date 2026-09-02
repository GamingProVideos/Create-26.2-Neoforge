# Pollution of the Realms / Advanced Chimneys compatibility

This optional configuration is for:

- Pollution of the Realms (`adpother`) 26.2.0.0
- Advanced Chimneys (`adchimneys`)
- Create 6.0.10 port for Minecraft 26.2

It sets every `carbon`, `sulfur`, and `dust` emission value in the supplied
Pollution of the Realms configuration to `0.0`. Advanced Chimneys can still
render its normal chimney smoke because its visual smoke is separate from the
three Pollution of the Realms pollutant types.

The Create Crushing Wheel Controller activity path is also updated from the
old `Inventory/ProcessingTime` path to the Minecraft 26.2 Create port's root
`ProcessingTime` value. The Blaze Burner (`burnTimeRemaining`), boiler
(`#hardcoded_heat_check`), and Millstone (`Timer`) activity definitions are
preserved.

## Install

1. Stop Minecraft.
2. Copy the included `adpother` folder into the instance's `config` folder.
3. Allow it to replace the existing `config/adpother` files.
4. Keep Advanced Chimneys installed for visible chimney smoke.
5. Start Minecraft again.

Back up an existing customized `config/adpother` folder before replacing it.
