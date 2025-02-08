package com.miguel.tibiamap.utils.lists

import com.miguel.tibiamap.domain.models.NPCModel

fun listNPC(): List<NPCModel> {
    val list = listOf(
        NPCModel(
            "Rashid",
            "https://static.wikia.nocookie.net/tibia/images/f/f5/Rashid.gif/revision/latest?cb=20221218120126&path-prefix=en&format=original",
        ),
//        NPCModel(
//            "Yasir",
//            "https://static.wikia.nocookie.net/tibia/images/4/4a/Yasir.gif/revision/latest?cb=20221211091332&path-prefix=en&format=original",
//        ),
        NPCModel(
            "Haroun",
            "https://static.wikia.nocookie.net/tibia/images/c/ce/Haroun.gif/revision/latest?cb=20230824232628&path-prefix=en&format=original",
        ),
        NPCModel(
            "Nah'Bob",
            "https://static.wikia.nocookie.net/tibia/images/d/dd/Nah%27Bob.gif/revision/latest?cb=20230824232703&path-prefix=en&format=original",
        ),
        NPCModel(
            "Asnarus",
            "https://static.wikia.nocookie.net/tibia/images/b/b7/Asnarus.gif/revision/latest?cb=20230822091743&path-prefix=en&format=original",
        ),
        NPCModel(
            "Alesar",
            "https://static.wikia.nocookie.net/tibia/images/a/a4/Alesar.gif/revision/latest?cb=20230725201630&path-prefix=en&format=original",
        ),
        NPCModel(
            "Yaman",
            "https://static.wikia.nocookie.net/tibia/images/7/7f/Yaman.gif/revision/latest?cb=20230822091739&path-prefix=en&format=original",
        ),
        NPCModel(
            "Esrik",
            "https://static.wikia.nocookie.net/tibia/images/8/83/Esrik.gif/revision/latest?cb=20230817115544&path-prefix=en&format=original",
        ),
        NPCModel(
            "Alexander",
            "https://static.wikia.nocookie.net/tibia/images/f/fe/Alexander.gif/revision/latest?cb=20221217020449&path-prefix=en&format=original",
        ),
        NPCModel(
            "Tamoril",
            "https://static.wikia.nocookie.net/tibia/images/a/a1/Tamoril.gif/revision/latest?cb=20230824232445&path-prefix=en&format=original",
        ),
        NPCModel(
            "Grizzly Adams",
            "https://static.wikia.nocookie.net/tibia/images/b/b5/Grizzly_Adams.gif/revision/latest?cb=20230831011705&path-prefix=en&format=original",
        )
    )
    return list
}