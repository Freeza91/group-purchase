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
    p params

    filename = "#{Time.now}" + @owner.tel
    File.open(fileroot + filename, "w") do |f|
      f.write(file.read)
    end

    @shop = Shop.create(:name => paramsInfor['name'], :address => paramsInfor['address'], 
                        :shop_tel => paramsInfor['shop_tel'], :category => paramsInfor['category'],
                        :profile => paramsInfor['profile'], :avatar => filename, 
                        :lat => 123.434, :lon => 34.4343,
                        :rating => 5,  :shopowner_id => session[:id])
    if ! @shop.nil?
      redirect_to 'shop'
    else 
      render 'new'
    end

  end

  put :update do
    p params
    file = params[:filename][:tempfile]
    p file
    paramsInfor = params[:shop]
    @shop = @owner.shop
    File.open(fileroot + @shop.avatar, "w") do |f|
      f.write(file.read)
    end
    @shop = @shop.update(:name => paramsInfor['name'], :address => paramsInfor['address'], 
                        :shop_tel => paramsInfor['shop_tel'], :category => paramsInfor['category'],
                        :profile => paramsInfor['profile'], 
                        :lat => 123.434, :lon => 34.4343,
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
