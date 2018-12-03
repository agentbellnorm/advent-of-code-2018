(ns advent-of-code-2018.day-03
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.common :refer :all]
            [clojure.set :refer :all]))

(def test-input "#1 @ 1,3: 4x4\n#2 @ 3,1: 4x4\n#3 @ 5,5: 2x2")
(def input "#1 @ 555,891: 18x12\n#2 @ 941,233: 16x14\n#3 @ 652,488: 16x25\n#4 @ 116,740: 13x14\n#5 @ 178,10: 5x3\n#6 @ 864,158: 10x24\n#7 @ 173,81: 20x15\n#8 @ 493,771: 20x25\n#9 @ 463,127: 15x28\n#10 @ 333,574: 13x12\n#11 @ 931,902: 13x27\n#12 @ 184,436: 23x17\n#13 @ 374,584: 10x10\n#14 @ 690,863: 12x20\n#15 @ 433,268: 28x23\n#16 @ 710,325: 14x16\n#17 @ 378,861: 17x10\n#18 @ 925,936: 28x28\n#19 @ 519,499: 12x25\n#20 @ 291,420: 12x21\n#21 @ 243,96: 14x11\n#22 @ 258,505: 15x21\n#23 @ 294,721: 11x27\n#24 @ 393,832: 25x23\n#25 @ 142,340: 26x28\n#26 @ 266,531: 10x26\n#27 @ 357,653: 12x23\n#28 @ 17,548: 12x23\n#29 @ 161,444: 17x27\n#30 @ 146,951: 20x22\n#31 @ 858,553: 29x16\n#32 @ 325,189: 13x18\n#33 @ 885,228: 20x26\n#34 @ 394,440: 25x11\n#35 @ 634,501: 24x24\n#36 @ 853,794: 13x12\n#37 @ 377,301: 23x10\n#38 @ 542,131: 21x26\n#39 @ 672,548: 20x29\n#40 @ 731,618: 27x22\n#41 @ 85,265: 28x11\n#42 @ 591,619: 13x18\n#43 @ 136,205: 21x19\n#44 @ 180,783: 11x24\n#45 @ 619,131: 19x26\n#46 @ 506,154: 18x27\n#47 @ 615,903: 22x29\n#48 @ 401,837: 18x13\n#49 @ 10,199: 23x29\n#50 @ 880,922: 17x10\n#51 @ 547,63: 26x26\n#52 @ 970,218: 14x15\n#53 @ 820,953: 22x24\n#54 @ 248,379: 24x19\n#55 @ 804,722: 23x18\n#56 @ 274,153: 21x10\n#57 @ 777,134: 27x28\n#58 @ 196,451: 29x28\n#59 @ 783,910: 10x28\n#60 @ 365,386: 25x23\n#61 @ 915,605: 17x11\n#62 @ 686,93: 14x24\n#63 @ 538,772: 12x22\n#64 @ 451,837: 15x25\n#65 @ 766,140: 20x17\n#66 @ 968,138: 16x13\n#67 @ 531,484: 19x25\n#68 @ 513,868: 20x23\n#69 @ 78,618: 27x25\n#70 @ 541,134: 3x4\n#71 @ 769,427: 17x18\n#72 @ 172,687: 12x28\n#73 @ 12,634: 18x10\n#74 @ 168,926: 20x16\n#75 @ 672,742: 25x10\n#76 @ 82,711: 21x22\n#77 @ 305,602: 10x24\n#78 @ 92,253: 29x27\n#79 @ 497,487: 19x10\n#80 @ 330,898: 18x19\n#81 @ 361,495: 10x22\n#82 @ 653,931: 18x16\n#83 @ 52,779: 19x21\n#84 @ 498,677: 11x15\n#85 @ 14,562: 20x19\n#86 @ 355,858: 27x11\n#87 @ 394,595: 29x20\n#88 @ 677,541: 28x18\n#89 @ 191,404: 7x3\n#90 @ 16,196: 23x18\n#91 @ 226,336: 19x21\n#92 @ 481,22: 14x20\n#93 @ 322,585: 23x28\n#94 @ 473,198: 27x13\n#95 @ 277,193: 28x19\n#96 @ 69,280: 28x23\n#97 @ 778,170: 26x26\n#98 @ 668,817: 21x25\n#99 @ 327,900: 16x23\n#100 @ 502,648: 24x13\n#101 @ 205,742: 29x21\n#102 @ 688,264: 26x19\n#103 @ 496,680: 22x27\n#104 @ 679,456: 13x10\n#105 @ 556,620: 12x29\n#106 @ 42,505: 28x13\n#107 @ 307,871: 27x14\n#108 @ 7,530: 12x17\n#109 @ 14,312: 24x16\n#110 @ 189,124: 16x14\n#111 @ 534,792: 24x14\n#112 @ 556,773: 23x11\n#113 @ 865,221: 29x20\n#114 @ 638,775: 19x25\n#115 @ 946,932: 28x23\n#116 @ 486,710: 10x21\n#117 @ 767,476: 26x21\n#118 @ 692,429: 18x12\n#119 @ 517,41: 20x23\n#120 @ 265,653: 25x14\n#121 @ 446,488: 10x17\n#122 @ 686,679: 17x12\n#123 @ 147,850: 22x15\n#124 @ 408,411: 18x10\n#125 @ 657,838: 13x12\n#126 @ 627,776: 16x17\n#127 @ 135,266: 13x15\n#128 @ 164,718: 13x10\n#129 @ 358,529: 26x19\n#130 @ 894,869: 29x11\n#131 @ 821,823: 12x16\n#132 @ 781,619: 6x4\n#133 @ 949,91: 12x10\n#134 @ 953,433: 26x19\n#135 @ 884,795: 28x17\n#136 @ 858,240: 14x27\n#137 @ 335,459: 22x20\n#138 @ 180,572: 14x21\n#139 @ 644,911: 19x12\n#140 @ 433,495: 19x29\n#141 @ 32,223: 14x22\n#142 @ 664,95: 25x23\n#143 @ 384,107: 29x16\n#144 @ 105,581: 19x19\n#145 @ 27,178: 15x23\n#146 @ 768,848: 20x26\n#147 @ 660,193: 13x16\n#148 @ 7,64: 24x19\n#149 @ 593,106: 19x26\n#150 @ 662,368: 26x10\n#151 @ 317,415: 10x16\n#152 @ 88,768: 28x16\n#153 @ 263,429: 10x15\n#154 @ 287,531: 17x21\n#155 @ 68,119: 22x11\n#156 @ 733,484: 28x19\n#157 @ 924,65: 25x22\n#158 @ 865,714: 10x26\n#159 @ 230,927: 24x28\n#160 @ 534,465: 12x20\n#161 @ 457,131: 21x26\n#162 @ 704,190: 11x20\n#163 @ 531,878: 24x18\n#164 @ 328,36: 22x24\n#165 @ 976,135: 19x10\n#166 @ 961,587: 20x16\n#167 @ 310,528: 19x16\n#168 @ 834,852: 28x15\n#169 @ 708,647: 14x22\n#170 @ 259,312: 15x28\n#171 @ 165,253: 19x28\n#172 @ 587,467: 16x18\n#173 @ 157,710: 19x20\n#174 @ 698,411: 22x13\n#175 @ 287,541: 22x27\n#176 @ 868,317: 28x15\n#177 @ 774,143: 28x11\n#178 @ 140,557: 13x13\n#179 @ 181,633: 13x11\n#180 @ 295,826: 29x16\n#181 @ 10,610: 25x29\n#182 @ 148,150: 10x22\n#183 @ 231,927: 16x20\n#184 @ 886,130: 27x16\n#185 @ 879,263: 12x13\n#186 @ 374,462: 18x12\n#187 @ 805,923: 18x25\n#188 @ 743,914: 29x29\n#189 @ 604,791: 17x16\n#190 @ 776,854: 25x24\n#191 @ 67,603: 16x26\n#192 @ 540,328: 18x28\n#193 @ 96,583: 26x21\n#194 @ 972,222: 11x11\n#195 @ 957,356: 18x10\n#196 @ 945,656: 27x11\n#197 @ 565,368: 17x18\n#198 @ 195,219: 28x19\n#199 @ 789,730: 23x28\n#200 @ 630,46: 13x15\n#201 @ 824,417: 26x10\n#202 @ 559,775: 14x5\n#203 @ 435,750: 13x15\n#204 @ 661,203: 11x24\n#205 @ 181,625: 7x14\n#206 @ 358,576: 19x22\n#207 @ 476,651: 27x21\n#208 @ 66,517: 15x19\n#209 @ 633,213: 23x13\n#210 @ 430,208: 18x27\n#211 @ 102,491: 14x15\n#212 @ 623,147: 21x10\n#213 @ 600,397: 14x27\n#214 @ 231,960: 26x24\n#215 @ 273,537: 18x19\n#216 @ 502,240: 22x28\n#217 @ 797,498: 5x22\n#218 @ 933,854: 29x14\n#219 @ 708,268: 28x10\n#220 @ 817,812: 17x20\n#221 @ 606,49: 13x10\n#222 @ 6,344: 13x15\n#223 @ 737,701: 25x29\n#224 @ 842,34: 10x12\n#225 @ 750,729: 28x11\n#226 @ 438,842: 22x26\n#227 @ 394,925: 26x29\n#228 @ 886,235: 26x19\n#229 @ 712,18: 11x11\n#230 @ 598,42: 29x26\n#231 @ 754,869: 29x11\n#232 @ 486,504: 13x20\n#233 @ 11,191: 10x13\n#234 @ 223,194: 27x17\n#235 @ 157,238: 20x29\n#236 @ 915,41: 18x24\n#237 @ 52,592: 11x14\n#238 @ 673,628: 11x20\n#239 @ 449,114: 21x23\n#240 @ 234,910: 21x15\n#241 @ 253,890: 10x14\n#242 @ 788,449: 25x21\n#243 @ 168,161: 12x14\n#244 @ 187,798: 18x15\n#245 @ 947,143: 18x22\n#246 @ 38,930: 14x14\n#247 @ 801,712: 28x25\n#248 @ 860,604: 25x29\n#249 @ 68,386: 29x27\n#250 @ 371,506: 21x12\n#251 @ 822,968: 16x3\n#252 @ 884,721: 27x21\n#253 @ 789,675: 11x24\n#254 @ 144,219: 24x26\n#255 @ 9,95: 20x11\n#256 @ 626,974: 10x13\n#257 @ 737,619: 12x26\n#258 @ 492,223: 29x24\n#259 @ 175,630: 19x13\n#260 @ 84,626: 29x10\n#261 @ 691,691: 17x19\n#262 @ 120,910: 11x16\n#263 @ 301,168: 23x10\n#264 @ 353,610: 25x12\n#265 @ 769,657: 12x28\n#266 @ 37,17: 20x24\n#267 @ 76,322: 25x22\n#268 @ 638,487: 21x21\n#269 @ 516,145: 22x14\n#270 @ 109,511: 22x26\n#271 @ 559,456: 11x14\n#272 @ 43,666: 10x27\n#273 @ 621,740: 22x16\n#274 @ 875,216: 29x14\n#275 @ 414,271: 13x23\n#276 @ 42,189: 10x19\n#277 @ 517,764: 21x20\n#278 @ 151,269: 28x25\n#279 @ 504,261: 17x3\n#280 @ 255,355: 11x20\n#281 @ 122,257: 21x15\n#282 @ 843,311: 18x22\n#283 @ 955,82: 10x15\n#284 @ 750,250: 12x23\n#285 @ 969,354: 29x19\n#286 @ 969,820: 23x17\n#287 @ 474,955: 12x11\n#288 @ 633,494: 16x21\n#289 @ 841,382: 16x18\n#290 @ 125,133: 29x23\n#291 @ 218,232: 15x15\n#292 @ 40,43: 28x13\n#293 @ 232,921: 29x23\n#294 @ 460,307: 11x12\n#295 @ 147,212: 10x13\n#296 @ 952,562: 3x4\n#297 @ 192,943: 15x22\n#298 @ 481,498: 27x18\n#299 @ 789,86: 19x21\n#300 @ 610,844: 21x12\n#301 @ 520,532: 26x29\n#302 @ 407,181: 18x18\n#303 @ 13,66: 8x8\n#304 @ 341,547: 27x27\n#305 @ 351,688: 14x16\n#306 @ 201,442: 23x28\n#307 @ 342,904: 17x14\n#308 @ 509,322: 13x22\n#309 @ 63,247: 24x13\n#310 @ 272,424: 10x15\n#311 @ 747,433: 25x26\n#312 @ 257,405: 20x20\n#313 @ 237,973: 25x14\n#314 @ 553,465: 27x18\n#315 @ 172,640: 21x16\n#316 @ 690,939: 20x22\n#317 @ 264,379: 25x25\n#318 @ 38,472: 23x18\n#319 @ 928,590: 18x28\n#320 @ 13,86: 18x12\n#321 @ 259,21: 15x26\n#322 @ 37,134: 28x27\n#323 @ 338,103: 29x28\n#324 @ 439,220: 26x11\n#325 @ 202,640: 11x16\n#326 @ 134,734: 23x21\n#327 @ 249,964: 21x10\n#328 @ 451,446: 28x21\n#329 @ 246,643: 29x26\n#330 @ 756,212: 21x27\n#331 @ 361,687: 26x19\n#332 @ 579,241: 18x14\n#333 @ 410,112: 10x15\n#334 @ 553,941: 23x15\n#335 @ 698,609: 11x3\n#336 @ 341,509: 27x14\n#337 @ 643,329: 17x17\n#338 @ 680,460: 17x13\n#339 @ 693,91: 18x14\n#340 @ 306,323: 23x20\n#341 @ 207,634: 16x12\n#342 @ 323,139: 29x11\n#343 @ 501,836: 14x29\n#344 @ 633,907: 20x11\n#345 @ 454,312: 10x15\n#346 @ 583,673: 25x29\n#347 @ 518,925: 26x29\n#348 @ 590,107: 19x12\n#349 @ 235,286: 25x21\n#350 @ 344,113: 29x12\n#351 @ 901,800: 29x28\n#352 @ 754,852: 26x27\n#353 @ 668,639: 12x25\n#354 @ 861,179: 10x26\n#355 @ 720,789: 26x27\n#356 @ 52,769: 22x15\n#357 @ 68,783: 25x28\n#358 @ 768,229: 11x25\n#359 @ 551,486: 26x24\n#360 @ 155,62: 10x26\n#361 @ 93,661: 14x16\n#362 @ 322,326: 17x26\n#363 @ 53,459: 19x25\n#364 @ 502,694: 18x27\n#365 @ 787,727: 25x19\n#366 @ 348,462: 20x21\n#367 @ 783,191: 11x10\n#368 @ 944,595: 29x27\n#369 @ 708,286: 27x17\n#370 @ 717,933: 26x24\n#371 @ 829,378: 21x17\n#372 @ 973,814: 26x25\n#373 @ 741,844: 15x21\n#374 @ 418,442: 19x21\n#375 @ 33,193: 25x20\n#376 @ 964,353: 24x15\n#377 @ 818,696: 13x14\n#378 @ 571,663: 16x14\n#379 @ 584,628: 21x18\n#380 @ 134,425: 10x10\n#381 @ 833,952: 11x25\n#382 @ 446,261: 19x27\n#383 @ 657,704: 15x14\n#384 @ 228,276: 17x24\n#385 @ 498,857: 12x13\n#386 @ 20,8: 12x27\n#387 @ 297,377: 20x11\n#388 @ 889,426: 14x17\n#389 @ 868,738: 24x27\n#390 @ 30,951: 14x12\n#391 @ 625,104: 22x21\n#392 @ 773,708: 20x13\n#393 @ 388,454: 14x17\n#394 @ 219,951: 27x29\n#395 @ 795,754: 13x26\n#396 @ 400,265: 28x21\n#397 @ 324,597: 10x18\n#398 @ 114,496: 17x25\n#399 @ 584,255: 28x19\n#400 @ 629,722: 14x16\n#401 @ 669,206: 18x10\n#402 @ 792,242: 20x21\n#403 @ 173,927: 24x11\n#404 @ 706,306: 18x22\n#405 @ 12,97: 15x24\n#406 @ 193,353: 18x23\n#407 @ 554,331: 23x15\n#408 @ 9,555: 27x21\n#409 @ 812,852: 25x20\n#410 @ 177,620: 16x25\n#411 @ 167,288: 16x10\n#412 @ 782,741: 26x16\n#413 @ 620,779: 25x29\n#414 @ 272,764: 27x20\n#415 @ 688,557: 11x18\n#416 @ 83,269: 21x10\n#417 @ 319,557: 21x12\n#418 @ 895,133: 11x11\n#419 @ 75,383: 17x23\n#420 @ 82,19: 28x26\n#421 @ 263,264: 3x16\n#422 @ 66,214: 10x19\n#423 @ 223,142: 10x27\n#424 @ 150,611: 26x15\n#425 @ 36,115: 29x20\n#426 @ 490,543: 15x29\n#427 @ 196,896: 12x24\n#428 @ 675,740: 19x16\n#429 @ 893,219: 11x28\n#430 @ 157,923: 14x21\n#431 @ 297,590: 18x14\n#432 @ 102,742: 21x29\n#433 @ 908,858: 3x14\n#434 @ 567,655: 29x22\n#435 @ 649,111: 18x12\n#436 @ 26,943: 11x13\n#437 @ 492,228: 22x18\n#438 @ 846,753: 24x28\n#439 @ 755,911: 10x16\n#440 @ 526,883: 10x16\n#441 @ 914,48: 20x20\n#442 @ 849,402: 22x22\n#443 @ 441,234: 19x14\n#444 @ 521,419: 13x23\n#445 @ 595,53: 28x20\n#446 @ 635,912: 18x28\n#447 @ 501,71: 26x20\n#448 @ 897,30: 16x20\n#449 @ 802,59: 25x25\n#450 @ 768,98: 26x15\n#451 @ 927,94: 12x19\n#452 @ 119,558: 27x29\n#453 @ 722,503: 12x28\n#454 @ 982,805: 16x20\n#455 @ 779,613: 11x26\n#456 @ 945,834: 24x16\n#457 @ 935,927: 25x26\n#458 @ 234,229: 14x22\n#459 @ 609,126: 11x28\n#460 @ 937,568: 11x25\n#461 @ 947,846: 10x18\n#462 @ 458,642: 13x15\n#463 @ 513,108: 23x28\n#464 @ 914,264: 27x27\n#465 @ 349,99: 15x26\n#466 @ 793,682: 24x14\n#467 @ 290,217: 28x24\n#468 @ 692,738: 26x17\n#469 @ 808,438: 13x23\n#470 @ 732,496: 16x13\n#471 @ 805,698: 16x27\n#472 @ 71,280: 23x13\n#473 @ 68,222: 20x26\n#474 @ 395,363: 26x28\n#475 @ 727,915: 14x26\n#476 @ 550,982: 15x10\n#477 @ 126,422: 21x15\n#478 @ 295,92: 18x13\n#479 @ 320,166: 18x20\n#480 @ 179,162: 11x20\n#481 @ 237,966: 14x10\n#482 @ 336,581: 28x25\n#483 @ 572,553: 26x27\n#484 @ 359,380: 14x17\n#485 @ 47,660: 11x11\n#486 @ 393,115: 15x13\n#487 @ 924,289: 24x26\n#488 @ 300,819: 14x24\n#489 @ 410,235: 15x23\n#490 @ 576,622: 12x10\n#491 @ 335,486: 28x26\n#492 @ 662,99: 27x24\n#493 @ 584,702: 22x22\n#494 @ 253,289: 10x17\n#495 @ 197,324: 13x10\n#496 @ 869,407: 18x23\n#497 @ 300,188: 24x12\n#498 @ 36,36: 10x13\n#499 @ 852,415: 24x13\n#500 @ 101,13: 20x15\n#501 @ 123,511: 26x24\n#502 @ 197,154: 25x22\n#503 @ 22,263: 28x11\n#504 @ 220,173: 13x25\n#505 @ 41,392: 18x22\n#506 @ 543,685: 12x19\n#507 @ 406,201: 10x19\n#508 @ 790,674: 19x29\n#509 @ 312,452: 28x28\n#510 @ 280,437: 26x11\n#511 @ 812,949: 13x29\n#512 @ 816,946: 20x29\n#513 @ 400,824: 22x14\n#514 @ 158,602: 10x28\n#515 @ 372,914: 16x14\n#516 @ 376,597: 25x13\n#517 @ 877,651: 17x22\n#518 @ 111,493: 18x23\n#519 @ 913,37: 23x13\n#520 @ 264,809: 25x15\n#521 @ 406,266: 27x28\n#522 @ 431,729: 11x22\n#523 @ 17,343: 15x12\n#524 @ 906,340: 19x22\n#525 @ 45,744: 26x19\n#526 @ 726,496: 20x28\n#527 @ 821,578: 20x10\n#528 @ 240,944: 23x24\n#529 @ 412,202: 17x26\n#530 @ 646,211: 16x20\n#531 @ 246,498: 28x24\n#532 @ 270,398: 13x27\n#533 @ 131,471: 28x19\n#534 @ 498,559: 25x22\n#535 @ 865,31: 14x22\n#536 @ 644,714: 19x10\n#537 @ 466,744: 19x22\n#538 @ 366,566: 10x13\n#539 @ 189,402: 19x12\n#540 @ 596,526: 26x21\n#541 @ 223,795: 23x21\n#542 @ 69,637: 26x28\n#543 @ 450,675: 5x5\n#544 @ 847,395: 14x11\n#545 @ 266,401: 17x25\n#546 @ 161,329: 26x12\n#547 @ 600,144: 29x20\n#548 @ 558,76: 28x18\n#549 @ 304,224: 22x19\n#550 @ 383,295: 18x13\n#551 @ 331,915: 19x10\n#552 @ 972,342: 14x18\n#553 @ 398,245: 26x13\n#554 @ 604,392: 13x29\n#555 @ 365,642: 20x17\n#556 @ 963,525: 13x13\n#557 @ 250,262: 22x29\n#558 @ 355,459: 24x18\n#559 @ 503,647: 11x28\n#560 @ 482,723: 15x16\n#561 @ 374,445: 18x13\n#562 @ 661,618: 24x15\n#563 @ 219,464: 24x25\n#564 @ 483,88: 21x28\n#565 @ 714,173: 10x22\n#566 @ 537,636: 20x10\n#567 @ 32,529: 13x15\n#568 @ 735,749: 26x23\n#569 @ 511,241: 24x28\n#570 @ 150,14: 23x22\n#571 @ 829,680: 20x19\n#572 @ 222,68: 10x11\n#573 @ 753,809: 20x25\n#574 @ 22,822: 23x19\n#575 @ 534,461: 26x28\n#576 @ 866,641: 14x18\n#577 @ 356,218: 17x16\n#578 @ 143,420: 20x12\n#579 @ 450,414: 13x20\n#580 @ 538,604: 29x13\n#581 @ 736,715: 18x29\n#582 @ 173,683: 14x10\n#583 @ 313,887: 19x18\n#584 @ 905,340: 25x24\n#585 @ 14,531: 26x22\n#586 @ 328,889: 22x21\n#587 @ 282,247: 15x24\n#588 @ 85,272: 12x27\n#589 @ 103,413: 26x28\n#590 @ 801,685: 10x19\n#591 @ 47,583: 27x10\n#592 @ 250,597: 19x21\n#593 @ 259,885: 29x20\n#594 @ 816,180: 21x10\n#595 @ 263,314: 16x15\n#596 @ 636,730: 24x21\n#597 @ 556,613: 12x20\n#598 @ 286,401: 14x11\n#599 @ 431,73: 15x23\n#600 @ 896,856: 16x16\n#601 @ 631,907: 13x26\n#602 @ 981,906: 16x23\n#603 @ 606,684: 14x20\n#604 @ 230,214: 28x10\n#605 @ 157,689: 16x14\n#606 @ 38,191: 15x16\n#607 @ 286,768: 13x17\n#608 @ 161,972: 26x15\n#609 @ 272,817: 18x15\n#610 @ 613,974: 19x13\n#611 @ 980,898: 16x19\n#612 @ 359,457: 18x11\n#613 @ 414,909: 15x22\n#614 @ 586,412: 12x28\n#615 @ 961,210: 10x29\n#616 @ 55,499: 26x14\n#617 @ 770,43: 28x26\n#618 @ 819,21: 20x19\n#619 @ 948,585: 16x17\n#620 @ 522,808: 10x26\n#621 @ 457,175: 25x23\n#622 @ 323,836: 14x19\n#623 @ 22,620: 27x15\n#624 @ 195,345: 15x20\n#625 @ 115,588: 21x11\n#626 @ 588,48: 15x29\n#627 @ 33,935: 10x24\n#628 @ 606,430: 17x20\n#629 @ 480,193: 18x23\n#630 @ 897,338: 16x20\n#631 @ 540,926: 29x29\n#632 @ 957,384: 22x15\n#633 @ 304,729: 25x10\n#634 @ 534,944: 11x23\n#635 @ 836,447: 26x16\n#636 @ 572,896: 27x28\n#637 @ 386,101: 17x22\n#638 @ 635,806: 10x18\n#639 @ 215,281: 29x23\n#640 @ 184,632: 13x15\n#641 @ 242,638: 20x10\n#642 @ 337,466: 19x10\n#643 @ 260,700: 19x16\n#644 @ 8,834: 15x22\n#645 @ 359,538: 18x20\n#646 @ 596,27: 24x28\n#647 @ 279,157: 15x16\n#648 @ 671,98: 12x9\n#649 @ 741,871: 20x25\n#650 @ 141,852: 21x12\n#651 @ 689,298: 26x20\n#652 @ 843,276: 13x23\n#653 @ 228,814: 19x29\n#654 @ 672,749: 22x29\n#655 @ 862,912: 17x20\n#656 @ 942,317: 26x15\n#657 @ 304,367: 27x24\n#658 @ 328,210: 12x27\n#659 @ 565,243: 10x11\n#660 @ 429,940: 17x14\n#661 @ 153,679: 17x29\n#662 @ 835,437: 20x16\n#663 @ 529,774: 12x22\n#664 @ 848,303: 21x21\n#665 @ 503,55: 17x21\n#666 @ 239,416: 21x16\n#667 @ 444,361: 26x28\n#668 @ 838,261: 11x25\n#669 @ 687,873: 13x21\n#670 @ 237,352: 26x11\n#671 @ 417,134: 21x13\n#672 @ 938,76: 25x10\n#673 @ 182,482: 28x15\n#674 @ 492,214: 25x24\n#675 @ 410,678: 28x25\n#676 @ 233,632: 26x22\n#677 @ 658,768: 22x24\n#678 @ 501,322: 28x11\n#679 @ 236,324: 17x16\n#680 @ 94,627: 14x22\n#681 @ 363,281: 18x19\n#682 @ 222,418: 29x23\n#683 @ 960,306: 17x23\n#684 @ 356,569: 24x19\n#685 @ 593,915: 25x29\n#686 @ 494,438: 20x23\n#687 @ 918,936: 18x12\n#688 @ 626,135: 4x18\n#689 @ 782,67: 23x19\n#690 @ 675,944: 25x23\n#691 @ 68,478: 11x16\n#692 @ 14,606: 15x23\n#693 @ 546,392: 22x22\n#694 @ 789,871: 25x27\n#695 @ 743,690: 21x15\n#696 @ 959,220: 21x15\n#697 @ 355,757: 11x15\n#698 @ 510,319: 13x10\n#699 @ 476,178: 28x23\n#700 @ 421,763: 15x13\n#701 @ 344,614: 16x17\n#702 @ 524,432: 29x29\n#703 @ 320,506: 19x27\n#704 @ 824,404: 10x21\n#705 @ 862,413: 28x27\n#706 @ 652,101: 14x18\n#707 @ 453,90: 19x25\n#708 @ 954,383: 26x15\n#709 @ 335,359: 21x17\n#710 @ 476,108: 24x22\n#711 @ 86,817: 14x13\n#712 @ 953,850: 21x14\n#713 @ 900,341: 23x26\n#714 @ 885,128: 27x21\n#715 @ 416,257: 23x10\n#716 @ 539,705: 21x20\n#717 @ 76,249: 14x21\n#718 @ 357,492: 12x15\n#719 @ 404,367: 10x17\n#720 @ 293,243: 21x10\n#721 @ 878,778: 20x28\n#722 @ 550,248: 21x21\n#723 @ 779,709: 20x19\n#724 @ 391,96: 10x7\n#725 @ 840,259: 21x24\n#726 @ 305,209: 19x29\n#727 @ 936,510: 28x22\n#728 @ 212,155: 12x23\n#729 @ 595,708: 29x18\n#730 @ 368,660: 24x21\n#731 @ 99,689: 21x21\n#732 @ 394,351: 13x24\n#733 @ 570,626: 29x11\n#734 @ 217,938: 20x27\n#735 @ 133,531: 27x28\n#736 @ 241,576: 14x29\n#737 @ 579,235: 19x10\n#738 @ 103,325: 25x14\n#739 @ 236,653: 18x22\n#740 @ 100,73: 27x14\n#741 @ 23,512: 10x28\n#742 @ 226,502: 17x18\n#743 @ 569,738: 10x21\n#744 @ 886,791: 27x12\n#745 @ 510,916: 20x19\n#746 @ 195,438: 20x25\n#747 @ 214,74: 18x12\n#748 @ 868,918: 25x10\n#749 @ 35,307: 23x18\n#750 @ 629,714: 12x25\n#751 @ 99,452: 27x22\n#752 @ 909,30: 12x23\n#753 @ 948,560: 14x10\n#754 @ 29,832: 25x15\n#755 @ 518,823: 17x10\n#756 @ 423,806: 17x27\n#757 @ 369,411: 24x21\n#758 @ 805,927: 13x13\n#759 @ 544,145: 12x20\n#760 @ 307,112: 17x27\n#761 @ 93,88: 16x20\n#762 @ 392,347: 24x27\n#763 @ 496,418: 17x27\n#764 @ 935,905: 5x16\n#765 @ 437,296: 23x11\n#766 @ 622,769: 26x15\n#767 @ 284,578: 21x10\n#768 @ 177,274: 14x28\n#769 @ 505,135: 27x24\n#770 @ 157,451: 19x16\n#771 @ 898,57: 21x13\n#772 @ 645,356: 13x22\n#773 @ 753,60: 20x22\n#774 @ 440,366: 20x15\n#775 @ 631,150: 10x26\n#776 @ 896,107: 19x28\n#777 @ 932,223: 14x29\n#778 @ 764,212: 13x19\n#779 @ 98,308: 28x28\n#780 @ 490,54: 29x10\n#781 @ 871,248: 22x12\n#782 @ 89,119: 14x19\n#783 @ 862,293: 15x21\n#784 @ 891,134: 14x10\n#785 @ 493,148: 18x22\n#786 @ 707,99: 26x16\n#787 @ 687,646: 22x13\n#788 @ 851,110: 28x12\n#789 @ 622,795: 25x22\n#790 @ 393,669: 18x27\n#791 @ 507,687: 27x24\n#792 @ 738,489: 16x14\n#793 @ 239,908: 26x11\n#794 @ 799,164: 22x29\n#795 @ 257,386: 28x19\n#796 @ 854,462: 10x14\n#797 @ 731,324: 19x18\n#798 @ 394,277: 15x27\n#799 @ 337,331: 27x27\n#800 @ 40,21: 16x10\n#801 @ 834,315: 17x18\n#802 @ 593,483: 10x26\n#803 @ 871,195: 23x25\n#804 @ 831,962: 25x10\n#805 @ 716,708: 27x21\n#806 @ 340,250: 28x11\n#807 @ 328,443: 10x24\n#808 @ 17,898: 13x28\n#809 @ 167,903: 14x19\n#810 @ 27,832: 25x16\n#811 @ 420,609: 19x21\n#812 @ 476,41: 26x29\n#813 @ 260,383: 10x17\n#814 @ 804,450: 13x14\n#815 @ 221,221: 19x13\n#816 @ 145,206: 17x26\n#817 @ 150,551: 26x12\n#818 @ 160,627: 21x11\n#819 @ 231,339: 10x13\n#820 @ 261,3: 25x28\n#821 @ 328,822: 17x29\n#822 @ 71,722: 22x24\n#823 @ 339,462: 13x23\n#824 @ 650,361: 14x10\n#825 @ 236,241: 15x13\n#826 @ 729,923: 19x12\n#827 @ 158,685: 26x21\n#828 @ 576,946: 10x10\n#829 @ 598,92: 27x23\n#830 @ 708,318: 24x27\n#831 @ 604,665: 11x25\n#832 @ 801,23: 25x27\n#833 @ 146,949: 16x29\n#834 @ 424,690: 20x12\n#835 @ 583,250: 21x23\n#836 @ 932,358: 27x28\n#837 @ 960,447: 20x17\n#838 @ 418,746: 20x27\n#839 @ 10,822: 18x15\n#840 @ 131,230: 16x20\n#841 @ 180,963: 22x14\n#842 @ 680,419: 29x27\n#843 @ 329,507: 20x27\n#844 @ 99,584: 14x25\n#845 @ 157,13: 14x24\n#846 @ 596,237: 18x16\n#847 @ 258,648: 20x26\n#848 @ 608,126: 22x12\n#849 @ 696,848: 22x14\n#850 @ 669,884: 19x15\n#851 @ 166,629: 23x11\n#852 @ 783,631: 18x21\n#853 @ 42,512: 17x24\n#854 @ 230,416: 17x26\n#855 @ 745,808: 21x29\n#856 @ 879,723: 23x27\n#857 @ 858,450: 17x10\n#858 @ 96,4: 10x25\n#859 @ 255,320: 21x27\n#860 @ 160,773: 27x13\n#861 @ 383,782: 13x26\n#862 @ 508,371: 13x11\n#863 @ 138,533: 18x29\n#864 @ 588,436: 23x14\n#865 @ 401,589: 29x28\n#866 @ 928,603: 24x17\n#867 @ 560,958: 23x27\n#868 @ 756,927: 13x16\n#869 @ 313,703: 16x17\n#870 @ 515,75: 5x11\n#871 @ 462,451: 11x27\n#872 @ 519,537: 17x15\n#873 @ 308,93: 26x17\n#874 @ 843,251: 12x15\n#875 @ 860,19: 11x24\n#876 @ 898,590: 12x16\n#877 @ 889,731: 25x16\n#878 @ 893,422: 28x11\n#879 @ 754,249: 18x20\n#880 @ 97,496: 20x20\n#881 @ 243,223: 13x22\n#882 @ 316,116: 26x21\n#883 @ 351,655: 23x21\n#884 @ 418,740: 19x19\n#885 @ 352,690: 24x13\n#886 @ 22,50: 19x29\n#887 @ 905,856: 21x27\n#888 @ 788,896: 12x15\n#889 @ 48,500: 17x11\n#890 @ 175,429: 15x13\n#891 @ 324,405: 11x19\n#892 @ 288,818: 18x17\n#893 @ 202,317: 24x27\n#894 @ 725,915: 15x24\n#895 @ 332,441: 17x21\n#896 @ 372,567: 24x20\n#897 @ 524,300: 23x28\n#898 @ 25,613: 15x22\n#899 @ 697,855: 17x22\n#900 @ 4,656: 19x13\n#901 @ 46,933: 27x21\n#902 @ 797,48: 24x21\n#903 @ 888,363: 29x28\n#904 @ 542,883: 19x20\n#905 @ 925,354: 12x22\n#906 @ 988,662: 10x16\n#907 @ 198,731: 11x22\n#908 @ 872,423: 21x27\n#909 @ 962,826: 19x11\n#910 @ 764,112: 23x17\n#911 @ 726,318: 18x21\n#912 @ 58,912: 15x26\n#913 @ 685,76: 23x17\n#914 @ 426,227: 24x22\n#915 @ 920,357: 11x21\n#916 @ 369,673: 25x22\n#917 @ 476,211: 24x26\n#918 @ 397,287: 13x24\n#919 @ 600,28: 13x22\n#920 @ 131,340: 20x27\n#921 @ 176,129: 29x12\n#922 @ 678,650: 11x17\n#923 @ 461,939: 11x25\n#924 @ 97,4: 17x18\n#925 @ 544,549: 16x15\n#926 @ 423,693: 10x14\n#927 @ 836,808: 11x21\n#928 @ 821,943: 24x13\n#929 @ 348,901: 10x11\n#930 @ 484,631: 18x18\n#931 @ 467,650: 13x26\n#932 @ 195,942: 14x11\n#933 @ 361,291: 20x29\n#934 @ 186,441: 10x17\n#935 @ 926,199: 18x16\n#936 @ 842,601: 23x15\n#937 @ 794,257: 15x22\n#938 @ 767,226: 11x11\n#939 @ 877,266: 13x26\n#940 @ 755,39: 26x14\n#941 @ 754,684: 24x13\n#942 @ 515,441: 19x13\n#943 @ 311,815: 17x23\n#944 @ 442,956: 23x26\n#945 @ 551,401: 10x29\n#946 @ 392,402: 10x14\n#947 @ 240,495: 26x11\n#948 @ 689,684: 29x18\n#949 @ 695,693: 8x8\n#950 @ 834,570: 27x12\n#951 @ 410,126: 20x27\n#952 @ 241,911: 17x4\n#953 @ 47,444: 16x22\n#954 @ 106,246: 14x16\n#955 @ 718,505: 15x20\n#956 @ 684,644: 23x22\n#957 @ 293,536: 22x12\n#958 @ 553,567: 24x10\n#959 @ 239,287: 12x15\n#960 @ 768,106: 19x17\n#961 @ 696,519: 11x28\n#962 @ 325,824: 26x11\n#963 @ 775,26: 27x19\n#964 @ 473,754: 10x12\n#965 @ 202,806: 20x17\n#966 @ 240,919: 10x21\n#967 @ 2,656: 28x14\n#968 @ 351,749: 26x15\n#969 @ 801,142: 17x26\n#970 @ 501,478: 26x22\n#971 @ 705,135: 25x19\n#972 @ 123,741: 17x13\n#973 @ 881,924: 20x11\n#974 @ 97,826: 16x23\n#975 @ 962,395: 29x14\n#976 @ 700,850: 23x20\n#977 @ 852,546: 10x14\n#978 @ 310,863: 10x24\n#979 @ 263,122: 27x16\n#980 @ 827,355: 26x16\n#981 @ 773,317: 21x11\n#982 @ 849,452: 29x24\n#983 @ 367,904: 13x21\n#984 @ 393,356: 26x11\n#985 @ 758,677: 14x15\n#986 @ 142,246: 18x14\n#987 @ 336,59: 23x13\n#988 @ 268,741: 13x23\n#989 @ 595,797: 15x21\n#990 @ 899,540: 20x10\n#991 @ 709,386: 18x29\n#992 @ 754,928: 27x18\n#993 @ 416,195: 26x17\n#994 @ 575,927: 29x25\n#995 @ 170,857: 23x28\n#996 @ 395,361: 14x28\n#997 @ 242,955: 14x17\n#998 @ 244,278: 19x21\n#999 @ 849,41: 21x29\n#1000 @ 835,177: 16x12\n#1001 @ 324,540: 20x13\n#1002 @ 315,212: 29x12\n#1003 @ 460,410: 22x13\n#1004 @ 641,786: 16x11\n#1005 @ 886,583: 10x26\n#1006 @ 610,35: 13x29\n#1007 @ 392,844: 13x27\n#1008 @ 347,361: 21x29\n#1009 @ 359,676: 24x29\n#1010 @ 239,803: 17x23\n#1011 @ 777,187: 10x27\n#1012 @ 587,102: 12x16\n#1013 @ 105,507: 16x24\n#1014 @ 331,116: 23x25\n#1015 @ 120,922: 19x16\n#1016 @ 689,120: 17x25\n#1017 @ 108,235: 28x11\n#1018 @ 891,542: 11x21\n#1019 @ 155,75: 19x19\n#1020 @ 917,337: 21x28\n#1021 @ 884,572: 20x24\n#1022 @ 892,519: 29x26\n#1023 @ 585,120: 16x25\n#1024 @ 584,620: 23x11\n#1025 @ 107,753: 27x28\n#1026 @ 148,197: 13x22\n#1027 @ 19,775: 23x17\n#1028 @ 967,634: 16x27\n#1029 @ 703,333: 28x20\n#1030 @ 173,411: 12x23\n#1031 @ 359,24: 18x13\n#1032 @ 63,244: 20x14\n#1033 @ 345,520: 12x29\n#1034 @ 15,255: 20x11\n#1035 @ 603,724: 25x24\n#1036 @ 240,478: 14x25\n#1037 @ 591,883: 10x25\n#1038 @ 455,285: 20x17\n#1039 @ 495,674: 27x19\n#1040 @ 259,262: 15x24\n#1041 @ 321,561: 20x12\n#1042 @ 961,130: 21x14\n#1043 @ 472,352: 20x21\n#1044 @ 78,504: 24x19\n#1045 @ 101,777: 16x27\n#1046 @ 386,656: 23x25\n#1047 @ 842,777: 17x21\n#1048 @ 748,866: 13x20\n#1049 @ 42,71: 18x15\n#1050 @ 311,268: 26x13\n#1051 @ 662,926: 11x29\n#1052 @ 228,514: 25x16\n#1053 @ 388,92: 19x18\n#1054 @ 770,218: 24x28\n#1055 @ 363,501: 29x24\n#1056 @ 111,525: 12x28\n#1057 @ 279,759: 29x14\n#1058 @ 26,103: 28x27\n#1059 @ 273,225: 24x18\n#1060 @ 369,7: 21x19\n#1061 @ 562,81: 14x9\n#1062 @ 293,582: 15x12\n#1063 @ 401,363: 13x12\n#1064 @ 695,602: 18x18\n#1065 @ 76,209: 18x26\n#1066 @ 541,698: 27x29\n#1067 @ 51,227: 16x22\n#1068 @ 609,468: 14x23\n#1069 @ 861,458: 23x19\n#1070 @ 664,371: 20x10\n#1071 @ 393,305: 29x17\n#1072 @ 808,880: 12x21\n#1073 @ 713,160: 28x27\n#1074 @ 37,828: 10x12\n#1075 @ 656,564: 27x24\n#1076 @ 401,452: 10x19\n#1077 @ 535,0: 21x19\n#1078 @ 676,643: 10x13\n#1079 @ 249,316: 26x16\n#1080 @ 10,191: 21x16\n#1081 @ 976,398: 15x11\n#1082 @ 380,673: 20x21\n#1083 @ 546,2: 24x14\n#1084 @ 90,528: 25x24\n#1085 @ 934,211: 19x10\n#1086 @ 538,132: 10x10\n#1087 @ 911,418: 11x23\n#1088 @ 640,59: 23x25\n#1089 @ 298,463: 16x14\n#1090 @ 848,402: 17x28\n#1091 @ 698,7: 20x16\n#1092 @ 729,744: 10x26\n#1093 @ 482,364: 25x21\n#1094 @ 795,495: 11x29\n#1095 @ 18,158: 28x27\n#1096 @ 966,825: 13x14\n#1097 @ 337,912: 25x15\n#1098 @ 119,811: 15x11\n#1099 @ 369,935: 18x15\n#1100 @ 764,877: 17x15\n#1101 @ 498,417: 11x13\n#1102 @ 107,428: 18x29\n#1103 @ 719,330: 13x13\n#1104 @ 130,443: 10x29\n#1105 @ 624,911: 14x12\n#1106 @ 31,36: 18x18\n#1107 @ 160,936: 25x29\n#1108 @ 376,447: 10x5\n#1109 @ 900,361: 11x26\n#1110 @ 232,517: 11x9\n#1111 @ 507,235: 17x13\n#1112 @ 385,299: 16x27\n#1113 @ 901,592: 5x10\n#1114 @ 514,516: 27x25\n#1115 @ 494,703: 25x15\n#1116 @ 248,81: 17x16\n#1117 @ 246,875: 16x13\n#1118 @ 78,252: 23x12\n#1119 @ 67,251: 27x22\n#1120 @ 839,801: 10x25\n#1121 @ 428,69: 12x25\n#1122 @ 49,452: 28x20\n#1123 @ 339,105: 12x16\n#1124 @ 344,260: 18x24\n#1125 @ 420,743: 22x29\n#1126 @ 382,571: 18x22\n#1127 @ 166,547: 12x21\n#1128 @ 258,507: 5x9\n#1129 @ 400,354: 25x20\n#1130 @ 839,431: 17x14\n#1131 @ 299,687: 29x22\n#1132 @ 349,347: 16x24\n#1133 @ 74,460: 19x12\n#1134 @ 652,222: 26x24\n#1135 @ 578,616: 28x20\n#1136 @ 566,655: 15x11\n#1137 @ 741,837: 25x20\n#1138 @ 434,53: 10x17\n#1139 @ 867,220: 19x23\n#1140 @ 878,177: 20x19\n#1141 @ 439,376: 23x11\n#1142 @ 538,781: 11x24\n#1143 @ 508,365: 13x21\n#1144 @ 450,292: 18x12\n#1145 @ 789,216: 22x25\n#1146 @ 240,915: 16x23\n#1147 @ 807,452: 6x9\n#1148 @ 938,99: 18x13\n#1149 @ 105,622: 19x21\n#1150 @ 537,922: 15x25\n#1151 @ 826,361: 20x13\n#1152 @ 779,488: 10x13\n#1153 @ 765,82: 15x11\n#1154 @ 148,200: 27x24\n#1155 @ 239,883: 28x13\n#1156 @ 299,518: 16x18\n#1157 @ 814,17: 13x26\n#1158 @ 593,516: 12x14\n#1159 @ 486,694: 17x15\n#1160 @ 902,540: 24x16\n#1161 @ 934,267: 16x25\n#1162 @ 479,621: 18x29\n#1163 @ 731,437: 18x24\n#1164 @ 47,392: 28x12\n#1165 @ 539,673: 27x17\n#1166 @ 168,916: 27x27\n#1167 @ 783,59: 28x21\n#1168 @ 427,684: 17x13\n#1169 @ 503,684: 21x18\n#1170 @ 805,49: 20x19\n#1171 @ 141,330: 12x25\n#1172 @ 242,290: 5x8\n#1173 @ 572,744: 17x11\n#1174 @ 5,189: 15x22\n#1175 @ 790,304: 15x14\n#1176 @ 333,200: 22x19\n#1177 @ 392,916: 17x10\n#1178 @ 64,450: 11x29\n#1179 @ 574,264: 13x18\n#1180 @ 257,311: 17x27\n#1181 @ 581,686: 25x20\n#1182 @ 92,794: 23x11\n#1183 @ 967,804: 26x27\n#1184 @ 657,43: 14x24\n#1185 @ 832,395: 18x28\n#1186 @ 574,65: 18x12\n#1187 @ 183,872: 12x15\n#1188 @ 658,96: 19x14\n#1189 @ 77,585: 29x29\n#1190 @ 746,882: 13x27\n#1191 @ 617,769: 24x29\n#1192 @ 922,359: 15x22\n#1193 @ 441,928: 28x18\n#1194 @ 679,632: 21x17\n#1195 @ 175,697: 4x13\n#1196 @ 719,113: 14x15\n#1197 @ 712,808: 19x20\n#1198 @ 344,231: 19x17\n#1199 @ 317,254: 13x21\n#1200 @ 24,26: 14x18\n#1201 @ 76,321: 16x27\n#1202 @ 122,526: 20x14\n#1203 @ 447,361: 26x13\n#1204 @ 850,773: 19x20\n#1205 @ 636,37: 25x14\n#1206 @ 171,314: 15x19\n#1207 @ 676,82: 12x23\n#1208 @ 913,726: 16x14\n#1209 @ 101,78: 18x13\n#1210 @ 162,580: 23x18\n#1211 @ 939,560: 25x11\n#1212 @ 31,536: 19x20\n#1213 @ 758,79: 12x10\n#1214 @ 420,281: 25x25\n#1215 @ 596,475: 16x13\n#1216 @ 428,794: 25x17\n#1217 @ 526,759: 25x19\n#1218 @ 437,669: 23x25\n#1219 @ 944,849: 27x25\n#1220 @ 957,818: 23x22\n#1221 @ 560,41: 11x28\n#1222 @ 622,761: 18x25\n#1223 @ 519,156: 28x29\n#1224 @ 545,535: 28x20\n#1225 @ 902,123: 26x22\n#1226 @ 884,314: 17x25\n#1227 @ 719,335: 13x21\n#1228 @ 750,862: 27x14\n#1229 @ 162,854: 10x14\n#1230 @ 573,925: 29x20\n#1231 @ 92,447: 19x26\n#1232 @ 243,285: 29x20\n#1233 @ 625,770: 26x23\n#1234 @ 111,698: 20x17\n#1235 @ 199,881: 20x29\n#1236 @ 331,470: 11x23\n#1237 @ 870,438: 27x17\n#1238 @ 308,334: 21x12\n#1239 @ 944,946: 20x22\n#1240 @ 543,924: 5x7\n#1241 @ 271,409: 28x20\n#1242 @ 165,833: 16x28\n#1243 @ 882,373: 23x28\n#1244 @ 721,841: 27x27\n#1245 @ 510,41: 15x25\n#1246 @ 843,434: 16x21\n#1247 @ 776,617: 25x20\n#1248 @ 41,198: 11x11\n#1249 @ 8,904: 18x10\n#1250 @ 621,462: 22x22\n#1251 @ 920,273: 29x14\n#1252 @ 800,684: 13x12\n#1253 @ 562,876: 18x27\n#1254 @ 982,644: 14x25\n#1255 @ 313,320: 10x15\n#1256 @ 242,792: 17x23\n#1257 @ 534,98: 19x16\n#1258 @ 11,788: 12x20\n#1259 @ 648,558: 20x18\n#1260 @ 276,824: 29x25\n#1261 @ 501,670: 15x14\n#1262 @ 84,796: 23x26\n#1263 @ 385,780: 13x12\n#1264 @ 668,778: 13x24\n#1265 @ 207,488: 12x19\n#1266 @ 639,612: 28x29\n#1267 @ 597,458: 29x12\n#1268 @ 556,349: 22x24\n#1269 @ 255,237: 20x27\n#1270 @ 484,942: 14x22\n#1271 @ 575,400: 15x17\n#1272 @ 600,855: 15x19\n#1273 @ 767,431: 18x19\n#1274 @ 499,762: 18x23\n#1275 @ 35,85: 25x22\n#1276 @ 496,149: 28x14\n#1277 @ 437,55: 24x25\n#1278 @ 649,345: 22x13\n#1279 @ 732,819: 12x28\n#1280 @ 633,99: 27x14\n#1281 @ 72,251: 15x14\n#1282 @ 527,451: 10x11\n#1283 @ 159,69: 27x19\n#1284 @ 292,519: 26x17\n#1285 @ 255,714: 11x14\n#1286 @ 951,914: 21x23\n#1287 @ 214,264: 19x24\n#1288 @ 552,951: 10x10\n#1289 @ 367,933: 24x20\n#1290 @ 250,548: 28x10\n#1291 @ 123,81: 28x16\n#1292 @ 511,512: 19x29\n#1293 @ 740,904: 29x18\n#1294 @ 176,4: 10x15\n#1295 @ 733,148: 28x18\n#1296 @ 18,633: 10x23\n#1297 @ 85,214: 27x12\n#1298 @ 215,119: 14x26\n#1299 @ 872,100: 29x14\n#1300 @ 255,120: 16x10\n#1301 @ 413,912: 23x18\n#1302 @ 150,347: 29x29\n#1303 @ 490,402: 12x26\n#1304 @ 34,742: 23x20\n#1305 @ 746,884: 13x12\n#1306 @ 526,514: 20x21\n#1307 @ 771,864: 13x15\n#1308 @ 840,963: 14x18\n#1309 @ 116,813: 26x21")

(defn fabric
  [width height]
  (vec (replicate height (vec (replicate width 0)))))

(defn get-cut-map
  [x y w h]
  {:x x :y y :width w :height h})

(defn cut-square
  {:test (fn []
           (is (= (cut-square [0 0 0] 1) [0 1 0]))
           (is (= (cut-square [0 0 1] 2) [0 0 2]))
           )}
  [row col]
  (assoc row col (inc (nth row col))))

(defn update-row?
  {:test (fn []
           (is (not (update-row? 0 1 1)))
           (is (update-row? 0 1 2))
           (is (update-row? 1 1 1))
           (is (update-row? 2 4 3))
           (is (not (update-row? 2 5 3)))
           )}
  [y index height]
  (<= y index (dec (+ y height))))

(defn cut
  {:test (fn []
           (is (= (cut [[0 0 0]
                        [0 0 0]
                        [0 0 0]] (get-cut-map 0 0 1 1))
                  [[1 0 0]
                   [0 0 0]
                   [0 0 0]]))
           (is (= (cut [[10 0 0]
                        [0 0 0]
                        [0 0 0]] (get-cut-map 0 0 1 1))
                  [[11 0 0]
                   [0 0 0]
                   [0 0 0]]))
           (is (= (cut [[0 0 0]
                        [0 0 0]
                        [0 0 0]] (get-cut-map 1 1 2 2))
                  [[0 0 0]
                   [0 1 1]
                   [0 1 1]])))}
  [fabric cut-info]
  (map-indexed (fn [row-index row]
                 (if (update-row? (:y cut-info) row-index (:height cut-info))
                   (let [cols-to-update (range (:x cut-info) (+ (:x cut-info) (:width cut-info)))]
                     (reduce (fn [current-row col]
                               (cut-square current-row col))
                             row
                             cols-to-update))
                   row))
               fabric))

(defn claim-to-cut
  {:test (fn []
           (is (= (claim-to-cut "#44 @ 180,783: 11x24")
                  {:x 180 :y 783 :width 11 :height 24 :id 44}))
           )}
  [claim]
  {:x      (read-string (last (re-find #"\@\s(.*)," claim)))
   :y      (read-string (last (re-find #",(.*):" claim)))
   :width  (read-string (last (re-find #":\s(.*)x" claim)))
   :height (read-string (last (re-find #"x(.*)$" claim)))
   :id     (read-string (last (re-find #"#(.*)\s@" claim)))
   })

(defn squares-with-two-or-more-claims
  {:test (fn []
           (is (= (squares-with-two-or-more-claims (->> test-input
                                                        (read-input-as-strings)
                                                        (map #(claim-to-cut %))))
                  4))
           (is (= (squares-with-two-or-more-claims (->> input
                                                        (read-input-as-strings)
                                                        (map #(claim-to-cut %))))
                  109716))                                  ; first
           )}
  [claims]
  (let [initial-fabric (fabric 1500 1500)
        cut-fabric (reduce (fn [fabric claim-cut]
                             (cut fabric claim-cut)) initial-fabric claims)]
    (reduce (fn [acc row]
              (+ acc (->> row
                          (filter #(> % 1))
                          (count)))) 0 cut-fabric)))

(defn cut-only-once?
  {:test (fn []
           (is (cut-only-once? [[0 0 0]
                                   [0 1 1]
                                   [0 1 1]] (get-cut-map 1 1 2 2)))
           (is (not (cut-only-once? [[0 0 0]
                                        [1 1 0]
                                        [1 1 0]] (get-cut-map 1 1 2 2))
                    ))
           (is (not (cut-only-once? [[0 0 0]
                                        [0 1 1]
                                        [0 1 2]] (get-cut-map 1 1 2 2))
                    ))
           )}
  [fabric cut-info]
  (let [rows (subvec fabric (:y cut-info) (+ (:y cut-info) (:height cut-info)))]
    (every? true? (map (fn [row]
                         (= (vec (replicate (:width cut-info) 1))
                            (subvec row (:x cut-info) (+ (:x cut-info) (:width cut-info)))
                            )) rows))))

(defn lone-claim
  {:test (fn []
           (is (= (lone-claim (->> test-input
                                   read-input-as-strings
                                   (map #(claim-to-cut %)))) 3))
           (is (= (lone-claim (->> input
                                   read-input-as-strings
                                   (map #(claim-to-cut %)))) 124)))} ; second
  [claims]
  (let [cut-fabric (vec (reduce (fn [fabric claim-cut]
                                  (cut fabric claim-cut)) (fabric 1500 1500) claims))]
    (->> claims
         (map (fn [claim]
                (if (cut-only-once? cut-fabric claim)
                  (:id claim))))
         (filter #(not (nil? %)))
         (first))))
