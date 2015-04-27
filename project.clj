(defproject madetomeasure-api "0.1.0-SNAPSHOT"

            :description "FIXME: write description"
            :url "http://example.com/FIXME"

            :dependencies [[org.clojure/clojure "1.6.0"]
                           [ring-server "0.4.0"]
                           [selmer "0.8.2"]
                           [com.taoensso/timbre "3.4.0"]
                           [com.taoensso/tower "3.0.2"]
                           [markdown-clj "0.9.65"]
                           [environ "1.0.0"]
                           [im.chit/cronj "1.4.3"]
                           [compojure "1.3.3"]
                           [ring/ring-defaults "0.1.4"]
                           [ring/ring-session-timeout "0.1.0"]
                           [ring-middleware-format "0.5.0"]
                           [noir-exception "0.2.3"]
                           [bouncer "0.3.2"]
                           [prone "0.8.1"]
                           [buddy "0.5.0"]
                           [yesql "0.5.0-rc1"]
                           [com.github.fge/json-schema-validator "2.2.6"]
                           [cheshire "5.4.0"]
                           [org.postgresql/postgresql "9.3-1102-jdbc41"]]

            :min-lein-version "2.0.0"
            :uberjar-name "madetomeasure-api.jar"
            :repl-options {:init-ns madetomeasure-api.handler}
            :jvm-opts ["-server"]

            :main madetomeasure-api.core

            :plugins [[lein-ring "0.9.1"]
                      [lein-environ "1.0.0"]
                      [lein-ancient "0.6.5"]
                      [clj-sql-up "0.3.7"]
                      [lein-cljfmt "0.1.10"]]


            :ring {:handler madetomeasure-api.handler/app
                   :init    madetomeasure-api.handler/init
                   :destroy madetomeasure-api.handler/destroy
                   :uberwar-name "madetomeasure-api.war"}

            :clj-sql-up {:database "jdbc:postgresql://localhost/madetomeasure_development?user=matthewkirk"
                         :database-test "jdbc:postgresql://localhost/madetomeasure_test?user=matthewkirk"
                         :deps [[org.postgresql/postgresql "9.3-1102-jdbc41"]]}

            :profiles
            {:uberjar {:omit-source true
                       :env {:production true}

                       :aot :all}
             :dev {:dependencies [[ring-mock "0.1.5"]
                                  [ring/ring-devel "1.3.2"]
                                  [pjstadig/humane-test-output "0.7.0"]
                                  ]
                   :source-paths ["env/dev/clj"]



                   :repl-options {:init-ns madetomeasure-api.repl}
                   :injections [(require 'pjstadig.humane-test-output)
                                (pjstadig.humane-test-output/activate!)]
                   :env {:dev true}}})

