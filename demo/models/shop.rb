class Shop < ActiveRecord::Base
  belongs_to :shopowner
  has_one :good
end
