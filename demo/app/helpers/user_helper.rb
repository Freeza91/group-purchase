# Helper methods defined here can be accessed in any controller or view in the application

module Demo
  class App
    module UserHelper

      def tanslate_shop shop 
        reply = {}
        reply['name'] = shop.name
        reply['address'] = shop.address
        reply['lat'] = shop.lat.to_s
        reply['lon'] = shop.lon.to_s
        reply['shop_tel'] = shop.shop_tel
        reply['rating'] = shop.rating
        reply['category'] = shop.category
        reply['avatar'] = shop.avatar
        reply['profile'] = shop.profile
        reply
      end

      def tanslate_good good 
        reply = {}
        reply['name'] = good.name
        reply['price'] = good.price.to_s
        reply['profile'] = good.profile
        reply['note'] = good.note
        reply['service'] = good.service
        reply['avatar'] = good.avatar.to_s
        reply['status'] = good.status.to_s
        reply['integration'] = good.integration.to_s
        reply
      end


    end

    helpers UserHelper
  end
end
