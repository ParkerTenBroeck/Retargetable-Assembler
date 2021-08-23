.local: ;this is an error


nonLocal:

.local:


nonLocal2:

.local:


.if false
.endif

this is a test \
to see if this works \


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

.mmsg "nested", "rep"

.endrep

.endrep


.emsg "this is a test", "LOOL"
.wmsg "jk its a warning"


.bruh 012 't' '\f'
.include "test2i.asm"

.macro $_12test 2-5+
    .rep 2
        .rep 2
            .mmsg "nested", "rep"
        .endrep
    .endrep
.endmacro

.macro _tes22t
    .macro $_12test 2-5+
        .rep 2
            .rep 2
                .mmsg "nested rep"
            .endrep
        .endrep
    .endmacro
.endmacro

.macro overlap 1-3
.endmacro

.macro overlap 2-5
.endmacro

.macro overlap 4+
.endmacro

add $4, $4, $4
test
trap 0

trap 0xFF
trap 012

trap 0.2
trap 0.3d