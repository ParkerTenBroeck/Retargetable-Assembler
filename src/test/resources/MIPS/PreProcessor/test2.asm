.rep 5

    .if true
        .exitrep
    .endif

    .mmsg "wewe"

.endrep

.rep 5

    .if false
        .exitrep
    .endif

    .notDir "butThisis2"

.endrep

.rep 5

    .thisnotDir "3 baby"

    .if true
        .exitrep
    .endif

.endrep

.rep 2

.rep 2

nestedRep(25)

.endrep

.endrep



.bruh 012 't' '\f'
.include "test2i.asm"

.macro _tes22t

.endm

.macro $_12test

.endm



add $4, $4, $4
test
trap 0

trap 0xFF
trap 012

trap 0.2
trap 0.3d