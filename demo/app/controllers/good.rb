Demo::App.controllers :good do
  
  before do
    login?
  end


  get :index do
    @good = @owner.shop
    render 'index'
  end

  post :create do
    p params
    file = params[:filename][:tempfile]
    p file
    infor = params['good']
    filename = "#{Time.now}" + @owner.tel
    File.open(fileroot + filename, "w") do |f|
      f.write(file.read)
    end
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
    p @good
    File.open( fileroot + @good.avatar, "w") do |f|
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
