{
 :dev {
   :env {
     :dev true
     ; FIXME (cmhobbs) convert these to canonical JDBC connection strings in the
     ;       following format:  jdbc:postgresql://username:password@hostname/dbname
     :database-url "jdbc:postgresql://localhost/madetomeasure_development?user=dev&password=madetomeasure"             
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

 :production {
  ; FIXME (cmhobbs) convert these to canonical JDBC connection strings in the
  ;       following format:  jdbc:postgresql://username:password@hostname/dbname
  :env {:database-url "jdbc:postgresql://localhost/madetomeasure?user=deploy"}
 }

 :uberjar {
   :omit-source true
   :env {:production true}
   :aot :all
 }
}
