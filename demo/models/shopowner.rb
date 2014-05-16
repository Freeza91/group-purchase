class Shopowner < ActiveRecord::Base
    has_one :shop

    validates_presence_of :password, :message => 'cann ot '
    validates_length_of   :password, :within => 4..40
end
