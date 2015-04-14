{
 :dev {
       :env {
             :dev true
             :database-url "jdbc:postgresql://localhost/madetomeasure_development?user=matthewkirk"
             }
       :dependencies [[ring-mock "0.1.5"]
                      [ring/ring-devel "1.3.2"]
                      [pjstadig/humane-test-output "0.7.0"]
                      ]
       :source-paths ["env/dev/clj"]



       :repl-options {:init-ns madetomeasure-api.repl}
       :injections [(require 'pjstadig.humane-test-output)
                    (pjstadig.humane-test-output/activate!)]
       }
 :production 
 {
  :env {:database-url "jdbc:postgresql://localhost/madetomeasure?user=matthewkirk"}
  }
 }

{
 :uberjar {
           :omit-source true
           :env {:production true}
           :aot :all
           }
 }
