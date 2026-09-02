# Port 19 - Restore both Chain Conveyor strands

Port 18 incorrectly treated the chain geometry contributed by the two connected
Chain Conveyor block entities as duplicate work and selected a single owner.

That is not how Create's Chain Conveyor geometry works. `calculateConnectionStats()`
uses the local connection direction and a +/-35 degree tangent offset around the
pulley. The block entity at the other end sees the opposite connection direction,
therefore its `ConnectionStats` describes the other tangent/return strand of the
same conveyor loop.

Port 19 restores chain submission from both endpoints while retaining the port 18
render-distance/frustum fixes and the safe package/network/performance changes.

Result: a normal connection renders both parallel chain strands again rather than
only one.
