(ns elgethub.html
  (:require [hiccup.core :as html]
            [hiccup.page :as page]
            [hiccup-bridge.core :as hb]))

(defn active-nav? [page-enum inactive-link]
  (let [link-type (-> inactive-link
                      second
                      second
                      :href
                      (clojure.string/split #"\.")
                      first
                      rest
                      (#(apply str %))
                      keyword)]
    (if (= link-type page-enum)
      [:li.active (second inactive-link)]
      inactive-link)))

(defn page-template [active-navbar content]
  (page/html5
   [:head
    [:meta {:charset "utf-8"}]
    [:title "El-Get Hub"]
    [:meta {:content "width=device-width, initial-scale=1.0", :name "viewport"}]
    [:link {:rel "stylesheet", :href "bootstrap/css/bootstrap.css"}]
    [:link {:rel "stylesheet", :href "css/flat-ui.css"}]
    [:link {:href "images/favicon.ico", :rel "shortcut icon"}]
    "<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
     <!--[if lt IE 9]>"
    [:script {:src "js/html5shiv.js"}]
    [:script {:src "js/respond.min.js"}]
    "<![endif]-->"]
   [:body
    [:nav.navbar.navbar-default {:role "navigation"}
     [:div.navbar-header
      [:button.navbar-toggle {:data-target "#navbar-collapse-01", :data-toggle "collapse", :type "button"}
       [:span.sr-only "Toggle navigation"]]
      [:a.navbar-brand {:href "/home.html"} "El-Get Hub"]]
     [:div#navbar-collapse-01.collapse.navbar-collapse
      [:ul.nav.navbar-nav
       (active-nav? active-navbar [:li [:a {:href "/home.html"} "Home"]])
       (active-nav? active-navbar [:li [:a {:href "/upload.html"} "Upload Recipes"]])
       (active-nav? active-navbar [:li [:a {:href "/search.html"} "Search Recipes"]])]]]
    [:div.container
     content
     [:script {:src "js/jquery-1.8.3.min.js"}]
     [:script {:src "js/jquery-ui-1.10.3.custom.min.js"}]
     [:script {:src "js/jquery.ui.touch-punch.min.js"}]
     [:script {:src "js/bootstrap.min.js"}]
     [:script {:src "js/bootstrap-select.js"}]
     [:script {:src "js/bootstrap-switch.js"}]
     [:script {:src "js/flatui-checkbox.js"}]
     [:script {:src "js/flatui-radio.js"}]
     [:script {:src "js/jquery.tagsinput.js"}]
     [:script {:src "js/jquery.placeholder.js"}]]]))

(defn home-content []
  [:div
   [:div.row
    [:div.col-lg-12
     [:h1#main-title "El-Get Hub"]
     [:h5 "Community collection of el-get recipes"]
     [:ul.list-inline.intro-social-buttons
      [:li
       [:a.btn.btn-default.btn-lg.btn-primary {:href "https://twitter.com/elgethub"}
        [:i.fa.fa-twitter.fa-fw]
        [:span.network-name "Twitter"]]]
      [:li
       [:a.btn.btn-default.btn-lg.btn-primary {:href "https://github.com/escherize/el-get-hub"}
        [:i.fa.fa-github.fa-fw]
        [:span.network-name "Github"]]]]
     [:div.content-section-a
      [:div.container
       [:div.row
        [:div.col-lg-5.col-sm-6
         [:hr.section-heading-spacer]
         [:div.clearfix]
         [:h2.section-heading "Rationale"]
         [:p.lead "The world needs an emacs that's social, and " [:em "easily"] " extensible. Face it, Emacs package managment needs some love."]]
        [:div.col-lg-5.col-lg-offset-2.col-sm-6
         [:img.img-responsive {:alt "", :src "img/ipad.png"}]]]]]
     [:div.content-section-b
      [:div.container
       [:div.row
        [:div.col-lg-5.col-lg-offset-1.col-sm-push-6.col-sm-6
         [:hr.section-heading-spacer]
         [:div.clearfix]
         [:h2.section-heading "What's a recipe?"]
         [:p.lead "A declarative way to describe a package, and setup for the package."]
         [:pre "\n(:name undo-tree\n       :description \"Treat undo history as a tree\"\n       :website \"http://www.dr-qubit.org/emacs.php\"\n       :type github\n       :pkgname \"akhayyat/emacs-undo-tree\"\n       :checkout \"v1.5\"\n       :after (progn\n                   (global-undo-tree-mode 1)))"] "Notice how we can setup keybindings and package specific config right in the recipe. Neat!"]
        [:div.col-lg-5.col-sm-pull-6.col-sm-6
         [:img.img-responsive {:alt "", :src "img/dog.png"}]]]]]
     [:div.content-section-a
      [:div.container
       [:div.row
        [:div.col-lg-5.col-sm-6
         [:hr.section-heading-spacer]
         [:div.clearfix]
         [:h2.section-heading "But El-Get has recipes built in?"]
         [:p.lead "\n\t\t\t\t\t\t\t\t\t\tEl-Get is the best package manager for emacs by far. However there is only "
          [:em "one"] " recipe per package. So if two people modify a recipe where should it go?  In El-Get Hub!"]]
        [:div.col-lg-5.col-lg-offset-2.col-sm-6
         [:img.img-responsive {:alt "", :src "img/phones.png"}]]]]]]]
   [:h1 "Sweet! keep me in the loop."]
   [:form {:method "post", :action "/"}
    [:row
     [:input.form-control.input-lg {:name "email", :type "email"}]
     [:input.btn-huge.btn-primary {:value "Thank You!", :type "submit"}]]]])


(defn upload-content []
  [:form {:action "/upload", :method "post"}
   [:h2 "Recipe Upload"]
   [:row
    [:label]
    [:h4 "Title (optional)"]
    [:input.form-control.input-hg
     {:placeholder "My Epic Recipe, with extra conifg", :name "title", :type "text"}]]
   [:br]
   [:row
    [:label]
    [:h4 "Recipe"]
    [:textarea.form-control.input-lg
     {:placeholder "(:name git-timemachine
 :description \"Step through historic versions of git controlled files\"
 :type github
 :checkout \"1.3\"
 :pkgname \"pidu/git-timemachine\"
 :after (progn
          (setq git-timemachine-abbreviation-length 5)))",
      :name "recipe",
      :type "text",
      :rows "10",
      :cols "80"}]]
   [:br]
   [:row
    [:button.btn.btn-hg.btn-primary.btn-embossed {:type "submit"} "Upload Recipe"]]])


(defn search-content []
  [:form {:action "/find", :method "get"}
   [:h2 "Recipe Search"]
   [:h5 "fill in only the fields you want to search:"]
   [:row
    [:label]
    [:h4 "Title"]
    [:input.form-control.input-hg {:placeholder "*My* Recipe's Name", :name "title", :type "text"}]]
   [:row
    [:label]
    [:h4 "Name"]
    [:input.form-control.input-hg {:placeholder "git-timemachine", :name "name", :type "text"}]]
   [:row
    [:label]
    [:h4 "Type"]
    [:input.form-control.input-hg {:placeholder "github", :name "type", :type "text"}]]
   [:br]
   [:row
    [:button.btn.btn-hg.btn-primary.btn-embossed {:type "submit"} "Search"]]])

(defn search-result-content [recipes]
  [:div
   (for [recipe recipes]
     [:pre recipe])])

(defn search-result-page [recipes]
  (page-template :search (search-result-content recipes)))

(defn home-page []
  (page-template :home (home-content)))

(defn upload-page []
  (page-template :upload (upload-content)))

(defn search-page []
  (page-template :search (search-content)))

(comment
 (hb/html->hiccup )


  (->> (spit "/Users/bcm/dev/el-get-hub/resources/public/a.html"))

  )
