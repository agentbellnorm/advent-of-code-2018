(ns advent-of-code-2018.day-02
  (:require [clojure.test :refer :all]
            [advent-of-code-2018.common :refer :all]
            [clojure.set :refer :all]))

(def input "qcsnyvpigkxmrdawlfdefotxbh\nqcsnyvligkymrdawljujfotxbh\nqmsnyvpigkzmrnawzjuefotxbh\nqosnyvpigkzmrnawljuefouxbh\nqcsnhlpigkzmrtawljuefotxbh\nqcsnyvpigkzmrdapljuyfotxih\nqcsnbvpiokzmrdawljuerotxbh\nqcfnyvmigkzmrdawljuefotdbh\nqcsnynpigkzmrdawljuefptxbp\nqcsgyapigkzmrdawljuafotxbh\nqcsnyvpigkzmrdapljueeotibh\nqcfnyvpigkzmndawljuwfotxbh\nqzsayvpigkzmrdawijuefotxbh\nqcsnsvpiekzmrdawljfefotxbh\nncsnyvpigkzmrdaaljuefotxzh\nqssnyvpigkzmrdawljuefotobg\nqcshyipigkzmrdajljuefotxbh\nqcsnyvtigkzmrdawljgeaotxbh\nqcsnkvpxgkzmrdawljuefltxbh\nqcsnyvpiikzmrdawljuwfoqxbh\nqcsnybpigwzmqdawljuefotxbh\nqcsiyvpipkzbrdawljuefotxbh\nqldnyvpigkzmrdzwljuefotxbh\nqcsnyvpwgkzcrdawljuefmtxbh\nqcsnyvnigkzmrdahmjuefotxbh\nqcsnydpigkzmrdazljuefotxnh\nqcsqyvavgkzmrdawljuefotxbh\nucsnyvpigkzmrdawljuefocxwh\nqcsnivpigrzmrdawljuefouxbh\ntcsnyvpibkzmrdawlkuefotxbh\nqcstytpigkzmrdawsjuefotxbh\nqcynyvpigkzmrdawlluefotjbh\nqcstyvpigkqrrdawljuefotxbh\nicsnyvpizkzmrcawljuefotxbh\nqcsnyvpimkzmrdavljuezotxbh\nqvsnoupigkzmrdawljuefotxbh\nqcsnyvpigkzmrdawwjuefftxgh\nqcpnyvpijkzmrdvwljuefotxbh\nqcsnyvpigkzmxdakdjuefotxbh\njcsvyvpigkqmrdawljuefotxbh\nqcwnyvpigczmrsawljuefotxbh\nqcsnyvpdgkzmrdawljuefoixbm\nqysnyvpigkzmrdmwljuefotxbp\nqcsnavpigkzmrdaxajuefotxbh\nqcsfkvpigkzmrdawlcuefotxbh\nqcsnyvpigkvmrdawljcefotpbh\nqcsnyvpiqkkmrdawlvuefotxbh\nqhsnyvpigkzmrdawnjuedotxbh\nqasnlvpigkzmrdawljuefotxkh\nqgsnyvpigkzmrdabpjuefotxbh\njcsnyvdigkzmrmawljuefotxbh\nqcsnivpigkzmrdawljuefonxth\nqcsnyjpigkzmrdawljgefotxmh\nqcstyvpigkzmrdacljuefovxbh\nqcsnvvpigkzmrdawljuewotrbh\nqcsnyvaigdzmrdawljueuotxbh\nqcsnyvpegkzmwdawljzefotxbh\nqcsnevpngkzmrdawlouefotxbh\nqcsnuvpigozmrdawljuefotdbh\nqgsnyvpigkzmqdayljuefotxbh\nqcsnyvpigkzmrdcwdjuofotxbh\nqcnnyvpigkzmrzawljuefstxbh\nqlsgyvpigkzmrdtwljuefotxbh\nqcsnyfpigkzlroawljuefotxbh\nqcsnkvwigkzmrdowljuefotxbh\nqcsnrvpigkzmrdawljuvfltxbh\nqcsnyvpigkzvreawljuefotxmh\nqcsrgvpigkzmrdawliuefotxbh\nqysnyvpigkzmrdawlxaefotxbh\nqcsnyvpigizmrdlwljuefotxbi\nqzsnyvpitkzmrdawljuefbtxbh\nqzgnyvpigkzmrdawljuefotxih\nqcsnyvpigkzmrdawlguefvtxbb\nqcsnyvpigkzmidawljuefouxjh\nqksnyvpigkzmrdawlruefotxhh\nqcsnyvpinkzmrdaaljuefotxah\nqcsnxvpigkzjrdawljuefhtxbh\nqcsnyvpigkzardawlgueuotxbh\nqcsnyvpiakzmrdpwljuefotxbt\nqcsnyvpigkzmrdawkjuefotxgb\nqcsnyvpigkzmrdawljuehocsbh\nqcsnsvpigktmrdawljuefotxvh\nqusnrvpigkzrrdawljuefotxbh\nqcsnyhiigkzmrdawrjuefotxbh\nqcsnavpigkzmrdawlfuefotxbz\nqcsnyvpigkzmmdamsjuefotxbh\nqcsnyvzigkzmrdcwljmefotxbh\nqcsnyvpigkzmriawljuefotbbe\nqcsnyvpigksmrdawljaefotxbd\nqcsnyvpigkzfrdawljuefoxxmh\nqcsnyvpygkrmrdawljuefotxbi\nqcsngvwigfzmrdawljuefotxbh\nqcsnyvpigkmkrdauljuefotxbh\nqcsnyvpigxzmrdgwljuefwtxbh\nqconyapigkzmrdaxljuefotxbh\nqcsnydpigkzwrdawljulfotxbh\nqcsnyvpimkzmmdawljuefotxch\nqcsnkspigkzmrdawgjuefotxbh\nqcsnyvpigkzmrdhwljfefbtxbh\nqcsnyipijkztrdawljuefotxbh\nqcseyvpigkrhrdawljuefotxbh\nqcsnyvpivkzmrdawljuefottbb\nqcsnyvpigkzmrdawlouefcjxbh\nqcsnyvpigkzmrgayljuefotxbm\nqcsnyvpvgkzmrdawrjujfotxbh\nqcsnyvpigkzmndawljuefqtxch\nqcsnyvpigbzmrdawljuefotibg\nqcsnyvpigkzmseawljuefotxbv\nqcsnwvpigkzmraawnjuefotxbh\nmcsnyvpiqkzmrdawljuefotlbh\nbcsnyvpigczmrdmwljuefotxbh\nqcsnyvpigkzmrtawljuegntxbh\nqcsnyvpijkzmrdawlmrefotxbh\nqdsnyvpfgkzmrdawljuekotxbh\nqcsnyvpigkzmrdawcjfegotxbh\nqcslyvphgkrmddawljuefotxbh\nqcsnyvpigkzmsdawkjuefojxbh\nqzsnyvpigkzmrzawljuefmtxbh\nqcsnyvpqgkzmcdawljuefttxbh\nqcsnyvpbgkpmrdawljuefoqxbh\nqcsnyvpigkemrdywljmefotxbh\nqcsnyypigkzmrdawljmefotxwh\njcsnyvhwgkzmrdawljuefotxbh\nqcsnyvpigkzmrdawljurlotxwh\nqcsnnvpigzzmrdawljuefotwbh\nhcsnyvpigkzmrdarljuefitxbh\nqcsnyvpilkzmrfawljuefotsbh\nqcsnynpigkzmldawijuefotxbh\nqcsnyvpkgkjmrdawljuefotxlh\nqcsnylpigkzprdawljgefotxbh\nqcsnyvpigkzmrrawljnefohxbh\nqcsnivpigkzmrqawlbuefotxbh\nqcsgyvpigkzmrfawljuefotbbh\nqccuyvpigkzmrdawyjuefotxbh\ngcsnyvpigkzjrdawljuefotxby\nqcsmyvpiekzbrdawljuefotxbh\nqcsnyvpzgkrmrdawljuefotxbs\nqesnyvpigkzmpdqwljuefotxbh\nqcsnyvpigqzmrdawljuefutibh\nqcdnyvpigkzirdawljfefotxbh\nqcsnyvpiukzmrcrwljuefotxbh\nqcsnbvpickzmrdswljuefotxbh\nqcsnyvpighzmrpadljuefotxbh\nqccnyvpigkzmrdawljudxotxbh\nqcsnyvpigkzmrdabljuesotxlh\nqcsnyvpigkzmrrawlruefozxbh\nqconyzpigkzmrdawljuefotjbh\nqclnyvpigkzmrdxwljuefotbbh\nqcsnygpigkzmrdawlhuefooxbh\nqcsnyvpigkzmvdawljuefntxnh\nqcskyvpigkzmreawljuefotubh\nqrsnyvpxgkzmrdawljuefotxbz\nqclnyvpigtamrdawljuefotxbh\nqcsnyvpigkzmrdawojxefoyxbh\nqcsnyvpinkzmrdakljuwfotxbh\nqcsnyvpiykzmedawljuefgtxbh\nqcsayvpigkcmrdawijuefotxbh\nqcsnyvuiekzmrdamljuefotxbh\nqcdnyvpigkzmrdawnjuefoxxbh\nqcsnfvpwgszmrdawljuefotxbh\nqcsnycpigkzmrdawljqefotxih\nqcslyvphgkrmrdawljuefotxbh\necsnyvpigkzmrdawykuefotxbh\nqcsayvpigkzmraawljuekotxbh\nqcsnyvpigkdmrdawljuewofxbh\nqcznyvpigkzqrdawljuefotxnh\nqcsnyvplgkzmrdawljiefotlbh\nqcsnyvpigkzmroewljuefotbbh\nqcvnyvpigkzvrdawujuefotxbh\nqcanyypigkzmrdaeljuefotxbh\nqcsnyvwigkzmrdewljuefotxqh\nqcsryvpigkvmrdawljuefotabh\npcsnyvpigkwmrdawljueforxbh\nqcsncvpigkzmrdawljuefotwmh\nqcsnyvpigozmrdawljudfozxbh\nqcsnynpigkzmrbawhjuefotxbh\nqcsnyvuigkzmrqawljuefotxch\nqcsnyvpickzmrdawljueeofxbh\nqcsnyvpigkzgrdawljueiouxbh\nqcsnyvpigkztrdawljuxnotxbh\nqcsnyvpigwzvrdawljfefotxbh\nqcsnyvpilkzmrdawljuefotxcz\nqcsnjvpigkzmrdawljuefoywbh\nqhsnyvpigyzmrdawljuhfotxbh\nqcsnyvpirkzmfdawljuffotxbh\nqcsjyvpigkzmvdawljuefotxzh\nqcszivpirkzmrdawljuefotxbh\nqwsnyvpigkzmtdawljuefetxbh\nqcrntvpigkzordawljuefotxbh\nqrsnyvpigkzmsdawljrefotxbh\nqcsnyviivkzmrdazljuefotxbh\necsnyvpigkzmrdawyjuefotxbw\nqnsnyvpkgkzmrdawljueqotxbh\nqcsyyppigkzmrdawljuefotxba\nqcsnyvpigkzhrdpwljuefouxbh\nucsnyvpigkzmrdawojuefouxbh\nqysnyvpigkzmrdawljukfotxbd\nqcjnyvpigkzmrdalljfefotxbh\nfcsnyapigkmmrdawljuefotxbh\nqcnnkvpigkzmrdawljuefctxbh\nocsnyvpigkzmsdawljuefotxbl\nqcsnyvpiakomrdawpjuefotxbh\nqcsnyvpigkzmrdawljvefbtxwh\nqcsnuvpigkzmvdfwljuefotxbh\nqcsnyapihkzmrdagljuefotxbh\nqzsnyvpigkzmrdawtjuefotxgh\nqcsnyvpigkzmrdawljuefomyah\nocsnyvpigkzqrdawljuefotxbt\nqnsnyvpigkzmrdawljvevotxbh\nicsnyvpigkzmrdawljuefntxbt\nqcsnyvpigkzdrdawljuefotbbm\nscsnyvpigkzmrgawljuofotxbh\nqcsnydpigkzmrdowljuefotkbh\nqcsnyvtikkzmrdawljuefolxbh\nqcsiyvpigkcmrddwljuefotxbh\nqyrnyvpigkzmodawljuefotxbh\npcsndvpfgkzmrdawljuefotxbh\nqcsnyvkigkhmriawljuefotxbh\nqcsnyvpigkzmsdmwlkuefotxbh\ndosnyvpigkzmrdawdjuefotxbh\nqcnnnvpigkzmrdzwljuefotxbh\nqcsnyvpivkumrdailjuefotxbh\nqcsnyvpigkzmrdswljuzfotxbz\nqcscynpigkzmrdawljuefotxbc\nqeanyvpigkzmrdawijuefotxbh\nqclnylpigkzmrdawljuefotxyh\nqcsnyvpigkzmrdawljbefowxbp\nqcsnyvpagkzmrdawljuefolebh\nqxsiyvpigkzmrdawljuefotxgh\nqcsnyvpigkynrdawljuefoqxbh\nqcsnevpigkzmrdxwgjuefotxbh\nqcsnyvpdgkzlrdawljeefotxbh\nqcsnyvpigkzmrgawljxbfotxbh\necsnyvpigkzmrdbwbjuefotxbh\nqcsnyvpigkzmraawujuefocxbh\nqcsnyvpihkzmrdawljuefouxbn\nfgsqyvpigkzmrdawljuefotxbh\nqcsnyvpigkmmrdawajuefotnbh\nqcsnyvvigkzmrdahljudfotxbh\nqcsnyvpixkzmrdqwljutfotxbh\nncsnyvpickzmrdawljuehotxbh\nqcsnyvpizkzmrdawlpuefotxbp\nwcsnyvfigkzmrdakljuefotxbh\nqcsnyvpigkznrdhwljupfotxbh\njcsnyvpigkpmzdawljuefotxbh\nqcsnyppigkkmrdawljujfotxbh\nqcsnyvpigkumrdaeljuefodxbh\nqcsnyvhigkzmrdrwljuefodxbh\nqcsnyvpigkacrdawtjuefotxbh\nqcsnyvpigkzmylawlquefotxbh")
(def test-input "abcdef\nbababc\nabbcde\nabcccd\naabcdd\nabcdee\nababab")
(def test-input2 "abcde\nfghij\nklmno\npqrst\nfguij\naxcye\nwvxyz")

(defn has-chars-that-occur-exactly-n-times?
  {:test (fn []
           (is (not (has-chars-that-occur-exactly-n-times? "abcdef" 2)))
           (is (not (has-chars-that-occur-exactly-n-times? "abcdef" 3)))
           (is (has-chars-that-occur-exactly-n-times? "bababc" 3))
           (is (has-chars-that-occur-exactly-n-times? "bababc" 2))
           (is (has-chars-that-occur-exactly-n-times? "abbcde" 2))
           (is (not (has-chars-that-occur-exactly-n-times? "abbcde" 3)))
           (is (has-chars-that-occur-exactly-n-times? "abcccd" 3))
           (is (not (has-chars-that-occur-exactly-n-times? "abcccd" 2)))
           (is (has-chars-that-occur-exactly-n-times? "aabcdd" 2))
           (is (has-chars-that-occur-exactly-n-times? "abcdee" 2))
           (is (not (has-chars-that-occur-exactly-n-times? "abcdee" 3)))
           (is (has-chars-that-occur-exactly-n-times? "ababab" 3))
           (is (not (has-chars-that-occur-exactly-n-times? "ababab" 2))))}
  [char-seq n]
  (->> char-seq
       frequencies
       vals
       (some #(= n %))))

(defn checksum
  {:test (fn []
           (is (= (checksum (read-input-as-strings test-input)) 12))
           (is (= (checksum (read-input-as-strings input)) 6422)))} ; part 1
  [box-ids]
  (apply * (vals (reduce (fn [acc-checksum box-id]
                           (let [two-times (has-chars-that-occur-exactly-n-times? box-id 2)
                                 three-times (has-chars-that-occur-exactly-n-times? box-id 3)]
                             (cond
                               (not-any? true? [two-times three-times]) acc-checksum
                               (and two-times three-times) {2 (inc (get acc-checksum 2)) 3 (inc (get acc-checksum 3))}
                               (and two-times (not three-times)) (update acc-checksum 2 inc)
                               (and (not two-times) three-times) (update acc-checksum 3 inc))))
                         {2 0 3 0} box-ids))))

(defn distance
  {:test (fn []
           (is (= (distance "abcde" "axcye") 2))
           (is (= (distance "fghij" "fguij") 1))
           (is (= (distance "fghij" "fghij") 0))
           (is (= (distance "abc" "bca") 3)))}
  [s1 s2]
  (reduce (fn [acc index]
            (if (= (nth s1 index) (nth s2 index))
              acc
              (inc acc)))
          0
          (range (count s1))))

(defn ordered-shared-letters
  {:test (fn []
           (is (= (ordered-shared-letters "abc" "abc") (vec "abc")))
           (is (= (ordered-shared-letters "fghij" "fguij") (vec "fgij"))))}
  [s1 s2]
  (reduce (fn [result index]
            (if (= (nth s1 index) (nth s2 index))
              (conj result (nth s1 index))
              result))
          []
          (range (count s1))))

(defn common-letters
  {:test (fn []
           (is (= (common-letters (read-input-as-strings test-input2)) (vec "fgij")))
           (is (= (common-letters (read-input-as-strings input)) (vec "qcslyvphgkrmdawljuefotxbh"))))} ; part 2
  [box-ids]
  (loop [[current-id & ids] box-ids]
    (let [distances (zipmap (map (fn [id] (distance id current-id)) ids) ids)]
      (if (get distances 1)
        (ordered-shared-letters current-id (get distances 1))
        (recur ids)))))
