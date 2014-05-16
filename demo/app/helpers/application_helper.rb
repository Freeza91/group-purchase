module Demo
  class App
    module ApplicationHelper
       def login?
          if session[:id].nil?
            redirect_to '/shopowner/login'
          else
            @owner = Shopowner.find(session[:id])
          end
       end

       def fileroot
          return Padrino.root + '/public/images/'
       end
    end

    helpers ApplicationHelper
  end
end