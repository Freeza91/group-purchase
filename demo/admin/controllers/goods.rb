Demo::Admin.controllers :goods do
  get :index do
    @title = "Goods"
    @goods = Good.all
    render 'goods/index'
  end

  get :new do
    @title = pat(:new_title, :model => 'good')
    @good = Good.new
    render 'goods/new'
  end

  post :create do
    @good = Good.new(params[:good])
    if @good.save
      @title = pat(:create_title, :model => "good #{@good.id}")
      flash[:success] = pat(:create_success, :model => 'Good')
      params[:save_and_continue] ? redirect(url(:goods, :index)) : redirect(url(:goods, :edit, :id => @good.id))
    else
      @title = pat(:create_title, :model => 'good')
      flash.now[:error] = pat(:create_error, :model => 'good')
      render 'goods/new'
    end
  end

  get :edit, :with => :id do
    @title = pat(:edit_title, :model => "good #{params[:id]}")
    @good = Good.find(params[:id])
    if @good
      render 'goods/edit'
    else
      flash[:warning] = pat(:create_error, :model => 'good', :id => "#{params[:id]}")
      halt 404
    end
  end

  put :update, :with => :id do
    @title = pat(:update_title, :model => "good #{params[:id]}")
    @good = Good.find(params[:id])
    if @good
      if @good.update_attributes(params[:good])
        flash[:success] = pat(:update_success, :model => 'Good', :id =>  "#{params[:id]}")
        params[:save_and_continue] ?
          redirect(url(:goods, :index)) :
          redirect(url(:goods, :edit, :id => @good.id))
      else
        flash.now[:error] = pat(:update_error, :model => 'good')
        render 'goods/edit'
      end
    else
      flash[:warning] = pat(:update_warning, :model => 'good', :id => "#{params[:id]}")
      halt 404
    end
  end

  delete :destroy, :with => :id do
    @title = "Goods"
    good = Good.find(params[:id])
    if good
      if good.destroy
        flash[:success] = pat(:delete_success, :model => 'Good', :id => "#{params[:id]}")
      else
        flash[:error] = pat(:delete_error, :model => 'good')
      end
      redirect url(:goods, :index)
    else
      flash[:warning] = pat(:delete_warning, :model => 'good', :id => "#{params[:id]}")
      halt 404
    end
  end

  delete :destroy_many do
    @title = "Goods"
    unless params[:good_ids]
      flash[:error] = pat(:destroy_many_error, :model => 'good')
      redirect(url(:goods, :index))
    end
    ids = params[:good_ids].split(',').map(&:strip)
    goods = Good.find(ids)
    
    if Good.destroy goods
    
      flash[:success] = pat(:destroy_many_success, :model => 'Goods', :ids => "#{ids.to_sentence}")
    end
    redirect url(:goods, :index)
  end
end
