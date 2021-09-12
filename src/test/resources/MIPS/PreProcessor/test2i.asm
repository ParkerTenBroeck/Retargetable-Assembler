;if0
.if true

    ;if1
    .if true
        if1right
    .elseif false
        if1wrong
    .elseif true
        if1wrong
    .else
        if1wrong
    .endif

    ;if2
    .if false
        if2wrong
    .elseif false
        if2wrong
    .elseif false
        if2wrong
    .else
        if2right
    .endif

.else
    if0Wrong
.endif

;if3
.if false
    if3wrong
.elseif false
    if3wrong
.elseif true
    if3right
.else
    if3wrong
.endif

;if4
.if false
    if4wrong
.elseif true
    if4right
.elseif false
    if4wrong
.else
    if4wrong
.endif

;if5
.if false
    if5wrong
.elseif false
    if5wrong
.elseif false
    if5wrong
.else
    if5right
.endif

;set to true for errors
.if true
    ;if6
    .if false
        if6wrong
    .elseif false
        if6wrong
    .elseif true
        if6wrong"(but kinda right)"
    .else
        if6wrong
    .elseif false
        if6wrong
    .endif

    ;if7
    .if false
        if7wrong
    .elseif false
        if7wrong
    .elseif false
        if7wrong
    .else
        if7wrong "(but kinda right)"
    .else
        if7wrong
    .endif
.endif

.mmsg "message"
.wmsg "warning"
.emsg "error"

'\\'
'\'''f'
pi
true
FALSE
.anotherTest "THis is a \n String"
.bruh 012 't' '\f'

brilliant