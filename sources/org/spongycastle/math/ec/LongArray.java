package org.spongycastle.math.ec;

import android.support.v4.media.session.PlaybackStateCompat;
import androidx.core.view.InputDeviceCompat;
import com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter;
import com.tutk.IOTC.AVIOCTRLDEFs;
import java.math.BigInteger;
import net.sqlcipher.database.SQLiteDatabase;
import org.glassfish.grizzly.compression.lzma.impl.Base;
import org.spongycastle.util.Arrays;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class LongArray implements Cloneable {
    private static final short[] c = {0, 1, 4, 5, 16, 17, 20, 21, 64, 65, 68, 69, 80, 81, 84, 85, 256, 257, 260, 261, 272, 273, 276, 277, 320, 321, 324, 325, 336, 337, 340, 341, 1024, 1025, 1028, 1029, 1040, 1041, 1044, 1045, 1088, 1089, 1092, 1093, 1104, 1105, 1108, 1109, 1280, 1281, 1284, 1285, 1296, 1297, 1300, 1301, 1344, 1345, 1348, 1349, 1360, 1361, 1364, 1365, 4096, 4097, 4100, 4101, 4112, 4113, 4116, 4117, 4160, 4161, 4164, 4165, 4176, 4177, 4180, 4181, 4352, 4353, 4356, 4357, 4368, 4369, 4372, 4373, 4416, 4417, 4420, 4421, 4432, 4433, 4436, 4437, 5120, 5121, 5124, 5125, 5136, 5137, 5140, 5141, 5184, 5185, 5188, 5189, 5200, 5201, 5204, 5205, 5376, 5377, 5380, 5381, 5392, 5393, 5396, 5397, 5440, 5441, 5444, 5445, 5456, 5457, 5460, 5461, 16384, 16385, 16388, 16389, 16400, 16401, 16404, 16405, 16448, 16449, 16452, 16453, 16464, 16465, 16468, 16469, 16640, 16641, 16644, 16645, 16656, 16657, 16660, 16661, 16704, 16705, 16708, 16709, 16720, 16721, 16724, 16725, 17408, 17409, 17412, 17413, 17424, 17425, 17428, 17429, 17472, 17473, 17476, 17477, 17488, 17489, 17492, 17493, 17664, 17665, 17668, 17669, 17680, 17681, 17684, 17685, 17728, 17729, 17732, 17733, 17744, 17745, 17748, 17749, 20480, 20481, 20484, 20485, 20496, 20497, 20500, 20501, 20544, 20545, 20548, 20549, 20560, 20561, 20564, 20565, 20736, 20737, 20740, 20741, 20752, 20753, 20756, 20757, 20800, 20801, 20804, 20805, 20816, 20817, 20820, 20821, 21504, 21505, 21508, 21509, 21520, 21521, 21524, 21525, 21568, 21569, 21572, 21573, 21584, 21585, 21588, 21589, 21760, 21761, 21764, 21765, 21776, 21777, 21780, 21781, 21824, 21825, 21828, 21829, 21840, 21841, 21844, 21845};
    private static final int[] d = {0, 1, 8, 9, 64, 65, 72, 73, 512, InputDeviceCompat.SOURCE_DPAD, 520, 521, 576, 577, 584, 585, 4096, 4097, 4104, 4105, 4160, 4161, 4168, 4169, 4608, 4609, 4616, 4617, 4672, 4673, 4680, 4681, 32768, 32769, 32776, 32777, 32832, 32833, 32840, 32841, 33280, 33281, 33288, 33289, 33344, 33345, 33352, 33353, 36864, 36865, 36872, 36873, 36928, 36929, 36936, 36937, 37376, 37377, 37384, 37385, 37440, 37441, 37448, 37449, 262144, 262145, 262152, 262153, 262208, 262209, 262216, 262217, 262656, 262657, 262664, 262665, 262720, 262721, 262728, 262729, 266240, 266241, 266248, 266249, 266304, 266305, 266312, 266313, 266752, 266753, 266760, 266761, 266816, 266817, 266824, 266825, 294912, 294913, 294920, 294921, 294976, 294977, 294984, 294985, 295424, 295425, 295432, 295433, 295488, 295489, 295496, 295497, 299008, 299009, 299016, 299017, 299072, 299073, 299080, 299081, 299520, 299521, 299528, 299529, 299584, 299585, 299592, 299593};
    private static final int[] f = {0, 1, 16, 17, 256, 257, Base.kNumLenSymbols, Base.kMatchMaxLen, 4096, 4097, 4112, 4113, 4352, 4353, 4368, 4369, 65536, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_VSAAS_PLAYBACK_INFO_RESP, 65552, 65553, 65792, 65793, 65808, 65809, 69632, 69633, 69648, 69649, 69888, 69889, 69904, 69905, 1048576, 1048577, 1048592, 1048593, 1048832, 1048833, 1048848, 1048849, 1052672, 1052673, 1052688, 1052689, 1052928, 1052929, 1052944, 1052945, 1114112, 1114113, 1114128, 1114129, 1114368, 1114369, 1114384, 1114385, 1118208, 1118209, 1118224, 1118225, 1118464, 1118465, 1118480, 1118481, 16777216, 16777217, InputDeviceCompat.SOURCE_JOYSTICK, 16777233, 16777472, 16777473, 16777488, 16777489, 16781312, 16781313, 16781328, 16781329, 16781568, 16781569, 16781584, 16781585, 16842752, 16842753, 16842768, 16842769, 16843008, 16843009, 16843024, 16843025, 16846848, 16846849, 16846864, 16846865, 16847104, 16847105, 16847120, 16847121, 17825792, 17825793, 17825808, 17825809, 17826048, 17826049, 17826064, 17826065, 17829888, 17829889, 17829904, 17829905, 17830144, 17830145, 17830160, 17830161, 17891328, 17891329, 17891344, 17891345, 17891584, 17891585, 17891600, 17891601, 17895424, 17895425, 17895440, 17895441, 17895680, 17895681, 17895696, 17895697, SQLiteDatabase.CREATE_IF_NECESSARY, 268435457, 268435472, 268435473, 268435712, 268435713, 268435728, BaseQuickAdapter.HEADER_VIEW, 268439552, 268439553, 268439568, 268439569, 268439808, 268439809, 268439824, 268439825, 268500992, 268500993, 268501008, 268501009, 268501248, 268501249, 268501264, 268501265, 268505088, 268505089, 268505104, 268505105, 268505344, 268505345, 268505360, 268505361, 269484032, 269484033, 269484048, 269484049, 269484288, 269484289, 269484304, 269484305, 269488128, 269488129, 269488144, 269488145, 269488384, 269488385, 269488400, 269488401, 269549568, 269549569, 269549584, 269549585, 269549824, 269549825, 269549840, 269549841, 269553664, 269553665, 269553680, 269553681, 269553920, 269553921, 269553936, 269553937, 285212672, 285212673, 285212688, 285212689, 285212928, 285212929, 285212944, 285212945, 285216768, 285216769, 285216784, 285216785, 285217024, 285217025, 285217040, 285217041, 285278208, 285278209, 285278224, 285278225, 285278464, 285278465, 285278480, 285278481, 285282304, 285282305, 285282320, 285282321, 285282560, 285282561, 285282576, 285282577, 286261248, 286261249, 286261264, 286261265, 286261504, 286261505, 286261520, 286261521, 286265344, 286265345, 286265360, 286265361, 286265600, 286265601, 286265616, 286265617, 286326784, 286326785, 286326800, 286326801, 286327040, 286327041, 286327056, 286327057, 286330880, 286330881, 286330896, 286330897, 286331136, 286331137, 286331152, 286331153};
    private static final int[] q = {0, 1, 32, 33, 1024, 1025, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETGUARD_REQ, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETGUARD_RESP, 32768, 32769, 32800, 32801, 33792, 33793, 33824, 33825, 1048576, 1048577, 1048608, 1048609, 1049600, 1049601, 1049632, 1049633, 1081344, 1081345, 1081376, 1081377, 1082368, 1082369, 1082400, 1082401, 33554432, InputDeviceCompat.SOURCE_HDMI, 33554464, 33554465, 33555456, 33555457, 33555488, 33555489, 33587200, 33587201, 33587232, 33587233, 33588224, 33588225, 33588256, 33588257, 34603008, 34603009, 34603040, 34603041, 34604032, 34604033, 34604064, 34604065, 34635776, 34635777, 34635808, 34635809, 34636800, 34636801, 34636832, 34636833, 1073741824, 1073741825, 1073741856, 1073741857, 1073742848, 1073742849, 1073742880, 1073742881, 1073774592, 1073774593, 1073774624, 1073774625, 1073775616, 1073775617, 1073775648, 1073775649, 1074790400, 1074790401, 1074790432, 1074790433, 1074791424, 1074791425, 1074791456, 1074791457, 1074823168, 1074823169, 1074823200, 1074823201, 1074824192, 1074824193, 1074824224, 1074824225, 1107296256, 1107296257, 1107296288, 1107296289, 1107297280, 1107297281, 1107297312, 1107297313, 1107329024, 1107329025, 1107329056, 1107329057, 1107330048, 1107330049, 1107330080, 1107330081, 1108344832, 1108344833, 1108344864, 1108344865, 1108345856, 1108345857, 1108345888, 1108345889, 1108377600, 1108377601, 1108377632, 1108377633, 1108378624, 1108378625, 1108378656, 1108378657};
    private static final long[] x = {0, 1, 128, 129, 16384, 16385, 16512, 16513, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE, 2097153, 2097280, 2097281, 2113536, 2113537, 2113664, 2113665, 268435456, 268435457, 268435584, 268435585, 268451840, 268451841, 268451968, 268451969, 270532608, 270532609, 270532736, 270532737, 270548992, 270548993, 270549120, 270549121, IjkMediaMeta.AV_CH_LOW_FREQUENCY_2, 34359738369L, 34359738496L, 34359738497L, 34359754752L, 34359754753L, 34359754880L, 34359754881L, 34361835520L, 34361835521L, 34361835648L, 34361835649L, 34361851904L, 34361851905L, 34361852032L, 34361852033L, 34628173824L, 34628173825L, 34628173952L, 34628173953L, 34628190208L, 34628190209L, 34628190336L, 34628190337L, 34630270976L, 34630270977L, 34630271104L, 34630271105L, 34630287360L, 34630287361L, 34630287488L, 34630287489L, 4398046511104L, 4398046511105L, 4398046511232L, 4398046511233L, 4398046527488L, 4398046527489L, 4398046527616L, 4398046527617L, 4398048608256L, 4398048608257L, 4398048608384L, 4398048608385L, 4398048624640L, 4398048624641L, 4398048624768L, 4398048624769L, 4398314946560L, 4398314946561L, 4398314946688L, 4398314946689L, 4398314962944L, 4398314962945L, 4398314963072L, 4398314963073L, 4398317043712L, 4398317043713L, 4398317043840L, 4398317043841L, 4398317060096L, 4398317060097L, 4398317060224L, 4398317060225L, 4432406249472L, 4432406249473L, 4432406249600L, 4432406249601L, 4432406265856L, 4432406265857L, 4432406265984L, 4432406265985L, 4432408346624L, 4432408346625L, 4432408346752L, 4432408346753L, 4432408363008L, 4432408363009L, 4432408363136L, 4432408363137L, 4432674684928L, 4432674684929L, 4432674685056L, 4432674685057L, 4432674701312L, 4432674701313L, 4432674701440L, 4432674701441L, 4432676782080L, 4432676782081L, 4432676782208L, 4432676782209L, 4432676798464L, 4432676798465L, 4432676798592L, 4432676798593L, 562949953421312L, 562949953421313L, 562949953421440L, 562949953421441L, 562949953437696L, 562949953437697L, 562949953437824L, 562949953437825L, 562949955518464L, 562949955518465L, 562949955518592L, 562949955518593L, 562949955534848L, 562949955534849L, 562949955534976L, 562949955534977L, 562950221856768L, 562950221856769L, 562950221856896L, 562950221856897L, 562950221873152L, 562950221873153L, 562950221873280L, 562950221873281L, 562950223953920L, 562950223953921L, 562950223954048L, 562950223954049L, 562950223970304L, 562950223970305L, 562950223970432L, 562950223970433L, 562984313159680L, 562984313159681L, 562984313159808L, 562984313159809L, 562984313176064L, 562984313176065L, 562984313176192L, 562984313176193L, 562984315256832L, 562984315256833L, 562984315256960L, 562984315256961L, 562984315273216L, 562984315273217L, 562984315273344L, 562984315273345L, 562984581595136L, 562984581595137L, 562984581595264L, 562984581595265L, 562984581611520L, 562984581611521L, 562984581611648L, 562984581611649L, 562984583692288L, 562984583692289L, 562984583692416L, 562984583692417L, 562984583708672L, 562984583708673L, 562984583708800L, 562984583708801L, 567347999932416L, 567347999932417L, 567347999932544L, 567347999932545L, 567347999948800L, 567347999948801L, 567347999948928L, 567347999948929L, 567348002029568L, 567348002029569L, 567348002029696L, 567348002029697L, 567348002045952L, 567348002045953L, 567348002046080L, 567348002046081L, 567348268367872L, 567348268367873L, 567348268368000L, 567348268368001L, 567348268384256L, 567348268384257L, 567348268384384L, 567348268384385L, 567348270465024L, 567348270465025L, 567348270465152L, 567348270465153L, 567348270481408L, 567348270481409L, 567348270481536L, 567348270481537L, 567382359670784L, 567382359670785L, 567382359670912L, 567382359670913L, 567382359687168L, 567382359687169L, 567382359687296L, 567382359687297L, 567382361767936L, 567382361767937L, 567382361768064L, 567382361768065L, 567382361784320L, 567382361784321L, 567382361784448L, 567382361784449L, 567382628106240L, 567382628106241L, 567382628106368L, 567382628106369L, 567382628122624L, 567382628122625L, 567382628122752L, 567382628122753L, 567382630203392L, 567382630203393L, 567382630203520L, 567382630203521L, 567382630219776L, 567382630219777L, 567382630219904L, 567382630219905L, 72057594037927936L, 72057594037927937L, 72057594037928064L, 72057594037928065L, 72057594037944320L, 72057594037944321L, 72057594037944448L, 72057594037944449L, 72057594040025088L, 72057594040025089L, 72057594040025216L, 72057594040025217L, 72057594040041472L, 72057594040041473L, 72057594040041600L, 72057594040041601L, 72057594306363392L, 72057594306363393L, 72057594306363520L, 72057594306363521L, 72057594306379776L, 72057594306379777L, 72057594306379904L, 72057594306379905L, 72057594308460544L, 72057594308460545L, 72057594308460672L, 72057594308460673L, 72057594308476928L, 72057594308476929L, 72057594308477056L, 72057594308477057L, 72057628397666304L, 72057628397666305L, 72057628397666432L, 72057628397666433L, 72057628397682688L, 72057628397682689L, 72057628397682816L, 72057628397682817L, 72057628399763456L, 72057628399763457L, 72057628399763584L, 72057628399763585L, 72057628399779840L, 72057628399779841L, 72057628399779968L, 72057628399779969L, 72057628666101760L, 72057628666101761L, 72057628666101888L, 72057628666101889L, 72057628666118144L, 72057628666118145L, 72057628666118272L, 72057628666118273L, 72057628668198912L, 72057628668198913L, 72057628668199040L, 72057628668199041L, 72057628668215296L, 72057628668215297L, 72057628668215424L, 72057628668215425L, 72061992084439040L, 72061992084439041L, 72061992084439168L, 72061992084439169L, 72061992084455424L, 72061992084455425L, 72061992084455552L, 72061992084455553L, 72061992086536192L, 72061992086536193L, 72061992086536320L, 72061992086536321L, 72061992086552576L, 72061992086552577L, 72061992086552704L, 72061992086552705L, 72061992352874496L, 72061992352874497L, 72061992352874624L, 72061992352874625L, 72061992352890880L, 72061992352890881L, 72061992352891008L, 72061992352891009L, 72061992354971648L, 72061992354971649L, 72061992354971776L, 72061992354971777L, 72061992354988032L, 72061992354988033L, 72061992354988160L, 72061992354988161L, 72062026444177408L, 72062026444177409L, 72062026444177536L, 72062026444177537L, 72062026444193792L, 72062026444193793L, 72062026444193920L, 72062026444193921L, 72062026446274560L, 72062026446274561L, 72062026446274688L, 72062026446274689L, 72062026446290944L, 72062026446290945L, 72062026446291072L, 72062026446291073L, 72062026712612864L, 72062026712612865L, 72062026712612992L, 72062026712612993L, 72062026712629248L, 72062026712629249L, 72062026712629376L, 72062026712629377L, 72062026714710016L, 72062026714710017L, 72062026714710144L, 72062026714710145L, 72062026714726400L, 72062026714726401L, 72062026714726528L, 72062026714726529L, 72620543991349248L, 72620543991349249L, 72620543991349376L, 72620543991349377L, 72620543991365632L, 72620543991365633L, 72620543991365760L, 72620543991365761L, 72620543993446400L, 72620543993446401L, 72620543993446528L, 72620543993446529L, 72620543993462784L, 72620543993462785L, 72620543993462912L, 72620543993462913L, 72620544259784704L, 72620544259784705L, 72620544259784832L, 72620544259784833L, 72620544259801088L, 72620544259801089L, 72620544259801216L, 72620544259801217L, 72620544261881856L, 72620544261881857L, 72620544261881984L, 72620544261881985L, 72620544261898240L, 72620544261898241L, 72620544261898368L, 72620544261898369L, 72620578351087616L, 72620578351087617L, 72620578351087744L, 72620578351087745L, 72620578351104000L, 72620578351104001L, 72620578351104128L, 72620578351104129L, 72620578353184768L, 72620578353184769L, 72620578353184896L, 72620578353184897L, 72620578353201152L, 72620578353201153L, 72620578353201280L, 72620578353201281L, 72620578619523072L, 72620578619523073L, 72620578619523200L, 72620578619523201L, 72620578619539456L, 72620578619539457L, 72620578619539584L, 72620578619539585L, 72620578621620224L, 72620578621620225L, 72620578621620352L, 72620578621620353L, 72620578621636608L, 72620578621636609L, 72620578621636736L, 72620578621636737L, 72624942037860352L, 72624942037860353L, 72624942037860480L, 72624942037860481L, 72624942037876736L, 72624942037876737L, 72624942037876864L, 72624942037876865L, 72624942039957504L, 72624942039957505L, 72624942039957632L, 72624942039957633L, 72624942039973888L, 72624942039973889L, 72624942039974016L, 72624942039974017L, 72624942306295808L, 72624942306295809L, 72624942306295936L, 72624942306295937L, 72624942306312192L, 72624942306312193L, 72624942306312320L, 72624942306312321L, 72624942308392960L, 72624942308392961L, 72624942308393088L, 72624942308393089L, 72624942308409344L, 72624942308409345L, 72624942308409472L, 72624942308409473L, 72624976397598720L, 72624976397598721L, 72624976397598848L, 72624976397598849L, 72624976397615104L, 72624976397615105L, 72624976397615232L, 72624976397615233L, 72624976399695872L, 72624976399695873L, 72624976399696000L, 72624976399696001L, 72624976399712256L, 72624976399712257L, 72624976399712384L, 72624976399712385L, 72624976666034176L, 72624976666034177L, 72624976666034304L, 72624976666034305L, 72624976666050560L, 72624976666050561L, 72624976666050688L, 72624976666050689L, 72624976668131328L, 72624976668131329L, 72624976668131456L, 72624976668131457L, 72624976668147712L, 72624976668147713L, 72624976668147840L, 72624976668147841L};
    static final byte[] y = {0, 1, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8};
    private long[] z;

    public LongArray(int intLen) {
        this.z = new long[intLen];
    }

    public LongArray(long[] ints) {
        this.z = ints;
    }

    public LongArray(long[] ints, int off, int len) {
        if (off == 0 && len == ints.length) {
            this.z = ints;
            return;
        }
        long[] jArr = new long[len];
        this.z = jArr;
        System.arraycopy(ints, off, jArr, 0, len);
    }

    public LongArray(BigInteger bigInt) {
        if (bigInt == null || bigInt.signum() < 0) {
            throw new IllegalArgumentException("invalid F2m field value");
        } else if (bigInt.signum() == 0) {
            this.z = new long[]{0};
        } else {
            byte[] barr = bigInt.toByteArray();
            int barrLen = barr.length;
            int barrStart = 0;
            if (barr[0] == 0) {
                barrLen--;
                barrStart = 1;
            }
            int intLen = (barrLen + 7) / 8;
            this.z = new long[intLen];
            int iarrJ = intLen - 1;
            int rem = (barrLen % 8) + barrStart;
            long temp = 0;
            int barrI = barrStart;
            if (barrStart < rem) {
                while (barrI < rem) {
                    temp = (temp << 8) | ((long) (barr[barrI] & 255));
                    barrI++;
                }
                this.z[iarrJ] = temp;
                iarrJ--;
            }
            while (iarrJ >= 0) {
                long temp2 = 0;
                int i = 0;
                while (i < 8) {
                    temp2 = (temp2 << 8) | ((long) (barr[barrI] & 255));
                    i++;
                    barrI++;
                }
                this.z[iarrJ] = temp2;
                iarrJ--;
            }
        }
    }

    public boolean s() {
        long[] a = this.z;
        if (a[0] != 1) {
            return false;
        }
        for (int i = 1; i < a.length; i++) {
            if (a[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean t() {
        long[] a = this.z;
        for (long j : a) {
            if (j != 0) {
                return false;
            }
        }
        return true;
    }

    public int p() {
        return q(this.z.length);
    }

    public int q(int from) {
        long[] a = this.z;
        int from2 = Math.min(from, a.length);
        if (from2 < 1) {
            return 0;
        }
        if (a[0] != 0) {
            do {
                from2--;
            } while (a[from2] == 0);
            return from2 + 1;
        }
        do {
            from2--;
            if (a[from2] != 0) {
                return from2 + 1;
            }
        } while (from2 > 0);
        return 0;
    }

    public int k() {
        int i = this.z.length;
        while (i != 0) {
            i--;
            long w = this.z[i];
            if (w != 0) {
                return (i << 6) + j(w);
            }
        }
        return 0;
    }

    private int l(int limit) {
        int i = (limit + 62) >>> 6;
        while (i != 0) {
            i--;
            long w = this.z[i];
            if (w != 0) {
                return (i << 6) + j(w);
            }
        }
        return 0;
    }

    /* JADX WARNING: type inference failed for: r3v3, types: [byte[]] */
    /* JADX WARNING: type inference failed for: r3v4, types: [byte, int] */
    /* JADX WARNING: type inference failed for: r3v6, types: [byte] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r3v6, types: [byte] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int j(long r5) {
        /*
            r0 = 32
            long r0 = r5 >>> r0
            int r0 = (int) r0
            if (r0 != 0) goto L_0x000a
            int r0 = (int) r5
            r1 = 0
            goto L_0x000c
        L_0x000a:
            r1 = 32
        L_0x000c:
            int r2 = r0 >>> 16
            if (r2 != 0) goto L_0x001e
            int r2 = r0 >>> 8
            byte[] r3 = y
            if (r2 != 0) goto L_0x0019
            byte r3 = r3[r0]
            goto L_0x001d
        L_0x0019:
            byte r3 = r3[r2]
            int r3 = r3 + 8
        L_0x001d:
            goto L_0x0030
        L_0x001e:
            int r3 = r2 >>> 8
            if (r3 != 0) goto L_0x0029
            byte[] r4 = y
            byte r4 = r4[r2]
            int r4 = r4 + 16
            goto L_0x002f
        L_0x0029:
            byte[] r4 = y
            byte r4 = r4[r3]
            int r4 = r4 + 24
        L_0x002f:
            r3 = r4
        L_0x0030:
            int r4 = r1 + r3
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.math.ec.LongArray.j(long):int");
    }

    private long[] L(int newLen) {
        long[] newInts = new long[newLen];
        long[] jArr = this.z;
        System.arraycopy(jArr, 0, newInts, 0, Math.min(jArr.length, newLen));
        return newInts;
    }

    public BigInteger S() {
        int usedLen = p();
        if (usedLen == 0) {
            return ECConstants.a;
        }
        long highestInt = this.z[usedLen - 1];
        byte[] temp = new byte[8];
        int barrI = 0;
        boolean trailingZeroBytesDone = false;
        for (int j = 7; j >= 0; j--) {
            byte thisByte = (byte) ((int) (highestInt >>> (j * 8)));
            if (trailingZeroBytesDone || thisByte != 0) {
                trailingZeroBytesDone = true;
                temp[barrI] = thisByte;
                barrI++;
            }
        }
        byte[] barr = new byte[(((usedLen - 1) * 8) + barrI)];
        for (int j2 = 0; j2 < barrI; j2++) {
            barr[j2] = temp[j2];
        }
        for (int iarrJ = usedLen - 2; iarrJ >= 0; iarrJ--) {
            long mi = this.z[iarrJ];
            int j3 = 7;
            while (j3 >= 0) {
                barr[barrI] = (byte) ((int) (mi >>> (j3 * 8)));
                j3--;
                barrI++;
            }
        }
        return new BigInteger(1, barr);
    }

    private static long M(long[] x2, int xOff, long[] z2, int zOff, int count, int shift) {
        int shiftInv = 64 - shift;
        long prev = 0;
        for (int i = 0; i < count; i++) {
            long next = x2[xOff + i];
            z2[zOff + i] = (next << shift) | prev;
            prev = next >>> shiftInv;
        }
        return prev;
    }

    public LongArray d() {
        if (this.z.length == 0) {
            return new LongArray(new long[]{1});
        }
        long[] ints = L(Math.max(1, p()));
        ints[0] = 1 ^ ints[0];
        return new LongArray(ints);
    }

    private void e(LongArray other, int otherDegree, int bits) {
        int otherLen = (otherDegree + 63) >>> 6;
        int words = bits >>> 6;
        int shift = bits & 63;
        if (shift == 0) {
            a(this.z, words, other.z, 0, otherLen);
            return;
        }
        long carry = i(this.z, words, other.z, 0, otherLen, shift);
        if (carry != 0) {
            long[] jArr = this.z;
            int i = otherLen + words;
            jArr[i] = jArr[i] ^ carry;
        }
    }

    private static long i(long[] x2, int xOff, long[] y2, int yOff, int count, int shift) {
        int shiftInv = 64 - shift;
        long prev = 0;
        for (int i = 0; i < count; i++) {
            long next = y2[yOff + i];
            int i2 = xOff + i;
            x2[i2] = x2[i2] ^ ((next << shift) | prev);
            prev = next >>> shiftInv;
        }
        return prev;
    }

    private static long h(long[] x2, int xOff, long[] y2, int yOff, int count, int shift) {
        int shiftInv = 64 - shift;
        long prev = 0;
        int i = count;
        while (true) {
            i--;
            if (i < 0) {
                return prev;
            }
            long next = y2[yOff + i];
            int i2 = xOff + i;
            x2[i2] = x2[i2] ^ ((next >>> shift) | prev);
            prev = next << shiftInv;
        }
    }

    public void f(LongArray other, int words) {
        int otherUsedLen = other.p();
        if (otherUsedLen != 0) {
            int minLen = otherUsedLen + words;
            if (minLen > this.z.length) {
                this.z = L(minLen);
            }
            a(this.z, words, other.z, 0, otherUsedLen);
        }
    }

    private static void a(long[] x2, int xOff, long[] y2, int yOff, int count) {
        for (int i = 0; i < count; i++) {
            int i2 = xOff + i;
            x2[i2] = x2[i2] ^ y2[yOff + i];
        }
    }

    private static void b(long[] x2, int xOff, long[] y2, int yOff, long[] z2, int zOff, int count) {
        for (int i = 0; i < count; i++) {
            z2[zOff + i] = x2[xOff + i] ^ y2[yOff + i];
        }
    }

    private static void c(long[] x2, int xOff, long[] y1, int y1Off, long[] y2, int y2Off, int count) {
        for (int i = 0; i < count; i++) {
            int i2 = xOff + i;
            x2[i2] = x2[i2] ^ (y1[y1Off + i] ^ y2[y2Off + i]);
        }
    }

    private static void o(long[] buf, int off, int bit, long word) {
        int n = (bit >>> 6) + off;
        int shift = bit & 63;
        if (shift == 0) {
            buf[n] = buf[n] ^ word;
            return;
        }
        buf[n] = buf[n] ^ (word << shift);
        long word2 = word >>> (64 - shift);
        if (word2 != 0) {
            int n2 = n + 1;
            buf[n2] = buf[n2] ^ word2;
        }
    }

    public boolean R() {
        long[] jArr = this.z;
        return jArr.length > 0 && (1 & jArr[0]) != 0;
    }

    private static boolean Q(long[] buf, int off, int n) {
        return (buf[off + (n >>> 6)] & (1 << (n & 63))) != 0;
    }

    private static void m(long[] buf, int off, int n) {
        int i = off + (n >>> 6);
        buf[i] = buf[i] ^ (1 << (n & 63));
    }

    private static void A(long a, long[] b, int bLen, long[] c2, int cOff) {
        int i = bLen;
        long[] jArr = c2;
        int i2 = cOff;
        if ((a & 1) != 0) {
            a(jArr, i2, b, 0, i);
        } else {
            long[] jArr2 = b;
        }
        int k = 1;
        long a2 = a;
        while (true) {
            long j = a2 >>> 1;
            long a3 = j;
            if (j != 0) {
                if ((a3 & 1) != 0) {
                    long carry = i(c2, cOff, b, 0, bLen, k);
                    if (carry != 0) {
                        int i3 = i2 + i;
                        jArr[i3] = jArr[i3] ^ carry;
                    }
                }
                k++;
                a2 = a3;
            } else {
                return;
            }
        }
    }

    public LongArray v(LongArray other, int m, int[] ks) {
        int aDeg;
        int bDeg;
        int i = m;
        int[] iArr = ks;
        int aDeg2 = k();
        if (aDeg2 == 0) {
            return this;
        }
        int bDeg2 = other.k();
        if (bDeg2 == 0) {
            return other;
        }
        LongArray A = this;
        LongArray B = other;
        if (aDeg2 > bDeg2) {
            A = other;
            B = this;
            int tmp = aDeg2;
            aDeg2 = bDeg2;
            bDeg2 = tmp;
        }
        int aLen = (aDeg2 + 63) >>> 6;
        int bLen = (bDeg2 + 63) >>> 6;
        int cLen = ((aDeg2 + bDeg2) + 62) >>> 6;
        if (aLen == 1) {
            long a0 = A.z[0];
            if (a0 == 1) {
                return B;
            }
            long[] c0 = new long[cLen];
            A(a0, B.z, bLen, c0, 0);
            return G(c0, 0, cLen, i, iArr);
        }
        int bMax = ((bDeg2 + 7) + 63) >>> 6;
        int[] ti = new int[16];
        long[] T0 = new long[(bMax << 4)];
        int tOff = bMax;
        ti[1] = tOff;
        System.arraycopy(B.z, 0, T0, tOff, bLen);
        int tOff2 = tOff;
        for (int i2 = 2; i2 < 16; i2++) {
            int i3 = tOff2 + bMax;
            tOff2 = i3;
            ti[i2] = i3;
            if ((i2 & 1) == 0) {
                M(T0, tOff2 >>> 1, T0, tOff2, bMax, 1);
            } else {
                b(T0, bMax, T0, tOff2 - bMax, T0, tOff2, bMax);
            }
        }
        long[] T1 = new long[T0.length];
        M(T0, 0, T1, 0, T0.length, 4);
        long[] a = A.z;
        long[] c2 = new long[(cLen << 3)];
        int aPos = 0;
        while (aPos < aLen) {
            long[] T02 = T0;
            long aVal = a[aPos];
            int cOff = aPos;
            while (true) {
                aDeg = aDeg2;
                long aVal2 = aVal >>> 4;
                bDeg = bDeg2;
                c(c2, cOff, T02, ti[((int) aVal) & 15], T1, ti[((int) aVal2) & 15], bMax);
                aVal = aVal2 >>> 4;
                if (aVal == 0) {
                    break;
                }
                cOff += cLen;
                aDeg2 = aDeg;
                bDeg2 = bDeg;
            }
            aPos++;
            T0 = T02;
            aDeg2 = aDeg;
            bDeg2 = bDeg;
        }
        int i4 = bDeg2;
        long[] T03 = T0;
        int cOff2 = c2.length;
        while (true) {
            int i5 = cOff2 - cLen;
            int cOff3 = i5;
            if (i5 != 0) {
                long[] jArr = c2;
                long[] c3 = c2;
                int i6 = cOff3 - cLen;
                int i7 = cOff3;
                int cOff4 = cOff3;
                long[] jArr2 = T03;
                i(jArr, i6, c3, i7, cLen, 8);
                c2 = c3;
                ti = ti;
                cOff2 = cOff4;
                a = a;
            } else {
                return G(c2, 0, cLen, i, iArr);
            }
        }
    }

    public LongArray z(LongArray other, int m, int[] ks) {
        int aDeg;
        int bDeg;
        int aDeg2 = k();
        if (aDeg2 == 0) {
            return this;
        }
        int bDeg2 = other.k();
        if (bDeg2 == 0) {
            return other;
        }
        LongArray A = this;
        LongArray B = other;
        if (aDeg2 > bDeg2) {
            A = other;
            B = this;
            int tmp = aDeg2;
            aDeg2 = bDeg2;
            bDeg2 = tmp;
        }
        int aLen = (aDeg2 + 63) >>> 6;
        int bLen = (bDeg2 + 63) >>> 6;
        int cLen = ((aDeg2 + bDeg2) + 62) >>> 6;
        if (aLen == 1) {
            long a0 = A.z[0];
            if (a0 == 1) {
                return B;
            }
            long[] c0 = new long[cLen];
            A(a0, B.z, bLen, c0, 0);
            return new LongArray(c0, 0, cLen);
        }
        int bMax = ((bDeg2 + 7) + 63) >>> 6;
        int[] ti = new int[16];
        long[] T0 = new long[(bMax << 4)];
        int tOff = bMax;
        ti[1] = tOff;
        System.arraycopy(B.z, 0, T0, tOff, bLen);
        int tOff2 = tOff;
        for (int i = 2; i < 16; i++) {
            int i2 = tOff2 + bMax;
            tOff2 = i2;
            ti[i] = i2;
            if ((i & 1) == 0) {
                M(T0, tOff2 >>> 1, T0, tOff2, bMax, 1);
            } else {
                b(T0, bMax, T0, tOff2 - bMax, T0, tOff2, bMax);
            }
        }
        long[] T1 = new long[T0.length];
        M(T0, 0, T1, 0, T0.length, 4);
        long[] a = A.z;
        long[] c2 = new long[(cLen << 3)];
        int aPos = 0;
        while (aPos < aLen) {
            int cOff = aPos;
            long aVal = a[aPos];
            while (true) {
                aDeg = aDeg2;
                long aVal2 = aVal >>> 4;
                bDeg = bDeg2;
                c(c2, cOff, T0, ti[((int) aVal) & 15], T1, ti[((int) aVal2) & 15], bMax);
                aVal = aVal2 >>> 4;
                if (aVal == 0) {
                    break;
                }
                cOff += cLen;
                aDeg2 = aDeg;
                bDeg2 = bDeg;
            }
            aPos++;
            aDeg2 = aDeg;
            bDeg2 = bDeg;
        }
        int i3 = bDeg2;
        int cOff2 = c2.length;
        while (true) {
            int i4 = cOff2 - cLen;
            cOff2 = i4;
            if (i4 == 0) {
                return new LongArray(c2, 0, cLen);
            }
            long[] jArr = c2;
            i(c2, cOff2 - cLen, c2, cOff2, cLen, 8);
            a = a;
            T0 = T0;
            ti = ti;
        }
    }

    public void B(int m, int[] ks) {
        long[] buf = this.z;
        int rLen = F(buf, 0, buf.length, m, ks);
        if (rLen < buf.length) {
            long[] jArr = new long[rLen];
            this.z = jArr;
            System.arraycopy(buf, 0, jArr, 0, rLen);
        }
    }

    private static LongArray G(long[] buf, int off, int len, int m, int[] ks) {
        return new LongArray(buf, off, F(buf, off, len, m, ks));
    }

    private static int F(long[] buf, int off, int len, int m, int[] ks) {
        int numBits;
        int len2;
        int numBits2;
        int vectorWiseWords;
        long[] jArr = buf;
        int i = off;
        int i2 = len;
        int i3 = m;
        int[] iArr = ks;
        int mLen = (i3 + 63) >>> 6;
        if (i2 < mLen) {
            return i2;
        }
        int numBits3 = Math.min(i2 << 6, (i3 << 1) - 1);
        int len3 = i2;
        int excessBits = (i2 << 6) - numBits3;
        while (excessBits >= 64) {
            len3--;
            excessBits -= 64;
        }
        int kLen = iArr.length;
        int kMax = iArr[kLen - 1];
        int kNext = kLen > 1 ? iArr[kLen - 2] : 0;
        int wordWiseLimit = Math.max(i3, kMax + 64);
        int vectorableWords = (Math.min(numBits3 - wordWiseLimit, i3 - kNext) + excessBits) >> 6;
        if (vectorableWords > 1) {
            int vectorWiseWords2 = len3 - vectorableWords;
            int vectorWiseWords3 = vectorWiseWords2;
            int i4 = vectorableWords;
            int i5 = numBits3;
            numBits2 = wordWiseLimit;
            H(buf, off, len3, vectorWiseWords2, m, ks);
            while (true) {
                vectorWiseWords = vectorWiseWords3;
                if (len3 <= vectorWiseWords) {
                    break;
                }
                len3--;
                jArr[i + len3] = 0;
                vectorWiseWords3 = vectorWiseWords;
            }
            numBits = len3;
            len2 = vectorWiseWords << 6;
        } else {
            int numBits4 = numBits3;
            numBits2 = wordWiseLimit;
            int i6 = numBits4;
            numBits = len3;
            len2 = i6;
        }
        if (len2 > numBits2) {
            K(buf, off, numBits, numBits2, m, ks);
            len2 = numBits2;
        }
        if (len2 > i3) {
            D(jArr, i, len2, i3, iArr);
        }
        return mLen;
    }

    private static void D(long[] buf, int off, int bitlength, int m, int[] ks) {
        while (true) {
            bitlength--;
            if (bitlength < m) {
                return;
            }
            if (Q(buf, off, bitlength)) {
                C(buf, off, bitlength, m, ks);
            }
        }
    }

    private static void C(long[] buf, int off, int bit, int m, int[] ks) {
        m(buf, off, bit);
        int n = bit - m;
        int j = ks.length;
        while (true) {
            j--;
            if (j >= 0) {
                m(buf, off, ks[j] + n);
            } else {
                m(buf, off, n);
                return;
            }
        }
    }

    private static void K(long[] buf, int off, int len, int toBit, int m, int[] ks) {
        int toPos = toBit >>> 6;
        int len2 = len;
        while (true) {
            int len3 = len2 - 1;
            if (len3 <= toPos) {
                break;
            }
            long word = buf[off + len3];
            if (word != 0) {
                buf[off + len3] = 0;
                I(buf, off, len3 << 6, word, m, ks);
            }
            len2 = len3;
        }
        int partial = toBit & 63;
        long word2 = buf[off + toPos] >>> partial;
        if (word2 != 0) {
            int i = off + toPos;
            buf[i] = buf[i] ^ (word2 << partial);
            I(buf, off, toBit, word2, m, ks);
        }
    }

    private static void I(long[] buf, int off, int bit, long word, int m, int[] ks) {
        int offset = bit - m;
        int j = ks.length;
        while (true) {
            j--;
            if (j >= 0) {
                o(buf, off, ks[j] + offset, word);
            } else {
                o(buf, off, offset, word);
                return;
            }
        }
    }

    private static void H(long[] buf, int off, int len, int words, int m, int[] ks) {
        int baseBit = (words << 6) - m;
        int j = ks.length;
        while (true) {
            int j2 = j - 1;
            if (j2 >= 0) {
                n(buf, off, buf, off + words, len - words, baseBit + ks[j2]);
                j = j2;
            } else {
                n(buf, off, buf, off + words, len - words, baseBit);
                return;
            }
        }
    }

    private static void n(long[] x2, int xOff, long[] y2, int yOff, int yLen, int bits) {
        int xOff2 = xOff + (bits >>> 6);
        int bits2 = bits & 63;
        if (bits2 == 0) {
            a(x2, xOff2, y2, yOff, yLen);
            return;
        }
        x2[xOff2] = x2[xOff2] ^ h(x2, xOff2 + 1, y2, yOff, yLen, 64 - bits2);
    }

    public LongArray w(int m, int[] ks) {
        int len = p();
        if (len == 0) {
            return this;
        }
        int _2len = len << 1;
        long[] r = new long[_2len];
        int pos = 0;
        while (pos < _2len) {
            long mi = this.z[pos >>> 1];
            int pos2 = pos + 1;
            r[pos] = r((int) mi);
            pos = pos2 + 1;
            r[pos2] = r((int) (mi >>> 32));
        }
        return new LongArray(r, 0, F(r, 0, r.length, m, ks));
    }

    public LongArray y(int n, int m, int[] ks) {
        int len = p();
        if (len == 0) {
            return this;
        }
        long[] r = new long[(((m + 63) >>> 6) << 1)];
        System.arraycopy(this.z, 0, r, 0, len);
        while (true) {
            n--;
            if (n < 0) {
                return new LongArray(r, 0, len);
            }
            O(r, len, m, ks);
            len = F(r, 0, r.length, m, ks);
        }
    }

    public LongArray N(int m, int[] ks) {
        int len = p();
        if (len == 0) {
            return this;
        }
        int _2len = len << 1;
        long[] r = new long[_2len];
        int pos = 0;
        while (pos < _2len) {
            long mi = this.z[pos >>> 1];
            int pos2 = pos + 1;
            r[pos] = r((int) mi);
            pos = pos2 + 1;
            r[pos2] = r((int) (mi >>> 32));
        }
        return new LongArray(r, 0, r.length);
    }

    private static void O(long[] x2, int xLen, int m, int[] ks) {
        int pos = xLen << 1;
        while (true) {
            xLen--;
            if (xLen >= 0) {
                long xVal = x2[xLen];
                int pos2 = pos - 1;
                x2[pos2] = r((int) (xVal >>> 32));
                pos = pos2 - 1;
                x2[pos] = r((int) xVal);
            } else {
                return;
            }
        }
    }

    private static long r(int x2) {
        short[] sArr = c;
        int r00 = sArr[x2 & 255] | (sArr[(x2 >>> 8) & 255] << 16);
        return ((((long) ((sArr[x2 >>> 24] << 16) | sArr[(x2 >>> 16) & 255])) & 4294967295L) << 32) | (4294967295L & ((long) r00));
    }

    public LongArray u(int m, int[] ks) {
        int i = m;
        int uzDegree = k();
        if (uzDegree == 0) {
            throw new IllegalStateException();
        } else if (uzDegree == 1) {
            return this;
        } else {
            LongArray uz = (LongArray) clone();
            int t = (i + 63) >>> 6;
            LongArray vz = new LongArray(t);
            C(vz.z, 0, i, i, ks);
            LongArray g1z = new LongArray(t);
            g1z.z[0] = 1;
            int[] uvDeg = {uzDegree, i + 1};
            LongArray[] uv = {uz, vz};
            int[] ggDeg = {1, 0};
            LongArray[] gg = {g1z, new LongArray(t)};
            int b = 1;
            int duv1 = uvDeg[1];
            int dgg1 = ggDeg[1];
            int j = duv1 - uvDeg[1 - 1];
            while (true) {
                if (j < 0) {
                    j = -j;
                    uvDeg[b] = duv1;
                    ggDeg[b] = dgg1;
                    b = 1 - b;
                    duv1 = uvDeg[b];
                    dgg1 = ggDeg[b];
                }
                int uzDegree2 = uzDegree;
                LongArray uz2 = uz;
                uv[b].e(uv[1 - b], uvDeg[1 - b], j);
                int duv2 = uv[b].l(duv1);
                if (duv2 == 0) {
                    return gg[1 - b];
                }
                int dgg2 = ggDeg[1 - b];
                int t2 = t;
                gg[b].e(gg[1 - b], dgg2, j);
                int dgg22 = dgg2 + j;
                if (dgg22 > dgg1) {
                    dgg1 = dgg22;
                } else if (dgg22 == dgg1) {
                    dgg1 = gg[b].l(dgg1);
                }
                j += duv2 - duv1;
                duv1 = duv2;
                int duv22 = m;
                uzDegree = uzDegree2;
                uz = uz2;
                t = t2;
            }
        }
    }

    public boolean equals(Object o) {
        if (!(o instanceof LongArray)) {
            return false;
        }
        LongArray other = (LongArray) o;
        int usedLen = p();
        if (other.p() != usedLen) {
            return false;
        }
        for (int i = 0; i < usedLen; i++) {
            if (this.z[i] != other.z[i]) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int usedLen = p();
        int hash = 1;
        for (int i = 0; i < usedLen; i++) {
            long mi = this.z[i];
            hash = (((hash * 31) ^ ((int) mi)) * 31) ^ ((int) (mi >>> 32));
        }
        return hash;
    }

    public Object clone() {
        return new LongArray(Arrays.l(this.z));
    }

    public String toString() {
        int i = p();
        if (i == 0) {
            return "0";
        }
        int i2 = i - 1;
        StringBuffer sb = new StringBuffer(Long.toBinaryString(this.z[i2]));
        while (true) {
            i2--;
            if (i2 < 0) {
                return sb.toString();
            }
            String s = Long.toBinaryString(this.z[i2]);
            int len = s.length();
            if (len < 64) {
                sb.append("0000000000000000000000000000000000000000000000000000000000000000".substring(len));
            }
            sb.append(s);
        }
    }
}
