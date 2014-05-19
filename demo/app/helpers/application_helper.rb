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


       def deal category
          main = ["美食", "酒店", "休闲娱乐", "丽人"]

          ans = main.index category
          if ans.nil?
            categorys = category
          else
            category_list = [
              ["火锅", "自助餐", "西餐", "日韩料理", "甜点", "烧烤烤鱼", "川湘菜", "江浙菜", "粤菜", "小吃快餐"],
              ["经济型酒店", "豪华型酒店"],
              ["电影", "KTV", "洗浴", "健身", "桌游", "密室逃脱", "酒吧", "演出赛事", "真人CS"],
              ["美发", "美甲", "美容SPA", "舞蹈"]
            ]

            categorys = category_list[ans]
          end

          categorys
       end
    end

    helpers ApplicationHelper
  end
end