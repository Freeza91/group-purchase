class CreateShops < ActiveRecord::Migration
  def self.up
    create_table :shops do |t|
      t.string :name
      t.string :address
      t.integer :shopowner_id
      t.decimal :lat
      t.decimal :lon
      t.string :shop_tel
      t.integer :rating
      t.string :category
      t.string :avatar
      t.string :profile
      t.timestamps
    end
  end

  def self.down
    drop_table :shops
  end
end
