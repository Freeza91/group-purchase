class CreateShopowners < ActiveRecord::Migration
  def self.up
    create_table :shopowners do |t|
      t.string :tel
      t.string :password
      t.string :nickname
      t.integer :income
      t.timestamps
    end
  end

  def self.down
    drop_table :shopowners
  end
end
