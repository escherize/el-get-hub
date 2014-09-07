(ns elgethub.html
  (:require [hiccup.core :as html]
            [hiccup.page :as page]
            [hiccup-bridge.core :as hb]))

(defn page-template [content]
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
      [:a.navbar-brand {:href "#"} "El-Get Hub"]]
     [:div#navbar-collapse-01.collapse.navbar-collapse
      [:ul.nav.navbar-nav
       [:li.active [:a {:href "/home.html"} "Home"]]
       [:li [:a {:href "/recipe.html"} "Upload Recipes"]]
       [:li [:a {:href "/search.html"} "Search Recipes"]]]] "<!-- /.navbar-collapse -->"] "<!-- /navbar -->"
    [:div.container "<!-- Start Content =============================================-->"
     content
     "<!-- End Content ===============================================-->"
     "<!-- Load JS here for greater good =============================-->"
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




(defn recipe-content []
  [:form {:action "/upload", :method "post"}
   [:h2 "Recipe Upload"]
   [:row
    [:label]
    [:h4 "Title (optional)"]
    [:input.form-control.input-hg
     {:placeholder "My Epic Recipe (w/ sauce)", :name "title", :type "text"}]]
   [:br]
   [:row
    [:label]
    [:h4 "Recipe"]
    [:textarea.form-control.input-lg
     {:placeholder "(:name git-timemachine
 :description \"Step through historic versions of git controlled files\"
 :type github
 :checkout \"1.3\"
 :pkgname \"pidu/git-timemachine\")",
      :name "recipe",
      :type "text",
      :rows "10",
      :cols "80"}]]
   [:br]
   [:row
    [:button.btn.btn-hg.btn-primary.btn-embossed {:type "submit"} "Upload Recipe"]]])

;; (hb/html->hiccup )

(comment
  
  (->> (recipe-content)
       page-template
       (spit "/Users/bcm/dev/el-get-hub/resources/public/a.html"))

  )
