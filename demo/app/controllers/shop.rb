Demo::App.controllers :shop do
  
  before :index, :create, :update, :edit, :new do
    login?
  end

  get :index do
    @shop = Shop.new
    render 'index'
  end

  post :create do
    file = params[:filename][:tempfile]
    paramsInfor = params[:shop]
    f = params[:filename][:filename].to_s.split('.').last
    t = Time.at(Time.now).strftime "%y%m%d%H%M%S"
    filename = t + @owner.tel + '.' + f 
    File.open(fileroot + filename, "wb") do |f|
      f.write(file.read)
    end
    location = paramsInfor['address_code'].to_s.split(',')
    lat = location[1]
    lon = location[0]

    @shop = Shop.create(:name => paramsInfor['name'], :address => paramsInfor['address'], 
                        :shop_tel => paramsInfor['shop_tel'], :category => paramsInfor['category'],
                        :profile => paramsInfor['profile'], :avatar => filename, 
                        :lat => lat, :lon => lon,
                        :rating => 5,  :shopowner_id => session[:id])
    if ! @shop.nil?
      redirect_to 'shop'
    else 
      render 'new'
    end

  end

  put :update do
    file = params[:filename][:tempfile]
    paramsInfor = params[:shop]
    @shop = @owner.shop
    File.open(fileroot + @shop.avatar, "wb") do |f|
      f.write(file.read)
    end

    location = paramsInfor['address_code'].to_s.split(',')
    lat = location[1]
    lon = location[0]
    @shop = @shop.update(:name => paramsInfor['name'], :address => paramsInfor['address'], 
                        :shop_tel => paramsInfor['shop_tel'], :category => paramsInfor['category'],
                        :profile => paramsInfor['profile'], 
                        :lat => lat, :lon => lon,
                        :rating => 5,  :shopowner_id => session[:id])

    if ! @shop.nil?
      redirect_to 'shop'
    else
      render :back
    end
  end

  get :new do
    @shop = Shop.new
    render 'new'
  end

  get :edit, :with => :id do 
    @shop = @owner.shop
    render 'edit'
  end

end
