(ns aoc2024-clojure.core)
(require '[clojure.java.io :as io])
(require '[clojure.string :as str])

(defn read-input
	[day]
	(slurp (io/resource (str (str day) ".input"))))

(defn print-day [day star result] (println (str "Day " day " Star " star " Result: " result)))

;Star 1
(def first-list (map (fn [line] (Integer/parseInt (first (str/split line #"   ")))) (str/split-lines (read-input 1))))
(def second-list (map (fn [line] (Integer/parseInt (second (str/split line #"   ")))) (str/split-lines (read-input 1))))

(print-day 1 1 (reduce + (map abs (map - (sort first-list) (sort second-list)))))

;Star 2
(print-day 1 2 (reduce +
											 (map
												 (fn [left] (* left (count (filter
																										 (fn [right] (= left right))
																										 second-list))))
												 first-list)))
;2-1
(defn is-safe [list]
	(- (first list) (second list))
	)

(defn check-safe-day-2 [list]
	(map is-safe (partition 2 1 list))
	)

(println (map check-safe-day-2 (map (fn [line] (map parse-long (str/split line #" ")))
							(str/split-lines (read-input 2)))))


;3-1
(defn calculate-mul [text] (reduce + (map (fn [find] (* (Integer/parseInt (nth find 1)) (Integer/parseInt (nth find 2))))
																					(re-seq #"mul\((\d{1,3}),(\d{1,3})\)" text)))
	)

(print-day 3 1 (calculate-mul (read-input 3)))

;3-2
(print-day 3 2
					 (reduce +
									 (map calculate-mul
												(conj
													; There can be Blocks with no or multiple do in between donts, so we check for nil and flatten
													(remove nil? (flatten
																				 (map
																					 (fn [block] (rest (str/split block #"do\(\)")))
																					 (str/split (read-input 3) #"don't\(\)"))
																				 )
																	) (first (str/split (read-input 3) #"don't\(\)")) ;the data starts with do enabled
													)
												)
									 ))