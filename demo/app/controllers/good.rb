Demo::App.controllers :good do
  
  before do
    login?
  end


  get :index do
    @good = @owner.shop
    render 'index'
  end

  post :create do
    file = params[:filename][:tempfile]
    f = params[:filename][:filename].to_s.split('.').last
    t = Time.at(Time.now).strftime "%y%m%d%H%M%S"
    filename = t + @owner.tel + '.' + f 
    File.open(fileroot + filename, "wb") do |f|
      f.write(file.read)
    end
    p infor
    @good = Good.create(:name => infor['name'], :shop_id => @owner.shop.id,
                        :price => infor['price'], :profile => infor['profile'],
                        :note => infor['note'], :service => infor['service'],
                        :avatar => filename, :status => false,
                        :integration => infor['integration'])
    if ! @good.nil?
      redirect_to 'good'
    else
      render 'new'
    end
  end

  put :update do
    file = params[:filename][:tempfile]
    infor = params['good']
    @good = @owner.shop.good
    File.open( fileroot + @good.avatar, "wb") do |f|
      f.write(file.read)
    end
    @good = @good.update(:name => infor['name'], :shop_id => @owner.shop.id,
                        :price => infor['price'], :profile => infor['profile'],
                        :note => infor['note'], :service => infor['service'],
                        :avatar => infor['avatar'], :status => false,
                        :integration => infor['integration'])
    if ! @good.nil?
      redirect_to 'good'
    else
      render :back
    end
  end

  get :edit, :with => :id do
    @good = @owner.shop.good
    render 'edit'
  end

  get :new do 
    @good = Good.new
    render 'new'
  end

end
