class CreateUsers < ActiveRecord::Migration
  def self.up
    create_table :users do |t|
      t.string :nickname
      t.string :tel
      t.string :password
      t.string :token
      t.timestamps
    end
  end

  def self.down
    drop_table :users
  end
end
