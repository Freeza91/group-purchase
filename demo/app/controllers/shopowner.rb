Demo::App.controllers :shopowner do


  before :index, :edit, :update, :loginout do 
    login?
  end
  
  get :index do
    @owner = Shopowner.find(session[:id])
    render 'index'
  end

  post :create do
    tel = params['shopowner']['tel']
    password = params['shopowner']['password']
    nickname = params['shopowner']['nickname']
    income = 0
    @owner = Shopowner.create(:tel => tel, :password => password, :nickname => nickname, :income => income)
    if @owner and ! @owner.created_at.nil?
      session[:id] = @owner.id
      redirect_to 'shopowner' 
    else 
      render 'new'
    end
  end

  get :new do
    @owner = Shopowner.new
    render 'new'
  end

  put :update do
    tel = params['shopowner']['tel']
    password = params['shopowner']['password']
    nickname = params['shopowner']['nickname']
    p session[:id]
    @owner = Shopowner.find(session[:id])
    if @owner 
      @owner = @owner.update(:tel => tel, :password => password, :nickname => nickname, :income => income)
    end
    if ! @owner.nil?
      render 'index'
    else 
      render "edit/#{session[:id]}"
    end
  end

  get :edit, :with => :id do
    @owner = Shopowner.find(session[:id])
    if ! @owner.nil?
      render 'new'
    else 
      redirect_to 'shopowner/new'
    end
  end

  get :loginout do 
    session.delete(:id)
    redirect_to 'shopowner/login'
  end

  post :logining do
    tel = params['shopowner']['tel']
    password = params['shopowner']['password']

    @owner = Shopowner.find_by(:tel => tel)
    if @owner and @owner.password == password
      session[:id] = @owner.id
      redirect_to 'shopowner'
    else 
      render 'login'
    end
  end

  get :login do 
    render 'login'
  end

end
